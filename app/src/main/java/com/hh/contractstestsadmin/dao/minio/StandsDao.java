package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.dao.minio.mapper.ServiceListMapper;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractArtefactKey;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.Service;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Optional.ofNullable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

public class StandsDao {

  private final MinioClient minioClient;
  private final ServiceListMapper serviceListMapper;
  private final Validator validator;
  private final Properties minioProperties;

  public StandsDao(MinioClient minioClient, Properties minioProperties, ServiceListMapper serviceListMapper) {
    this.minioClient = minioClient;
    this.minioProperties = minioProperties;
    this.serviceListMapper = serviceListMapper;
    ValidatorFactory factory = buildDefaultValidatorFactory();
    validator = factory.getValidator();

  }

  @NotNull
  public List<String> getStandNames() throws StandsDaoException {
    List<Bucket> standList;
    try {
      standList = minioClient.listBuckets();
    } catch (Exception e) {
      throw new StandsDaoException("It is impossible to get stands info from minio storage", e);
    }

    return ofNullable(standList)
        .orElseGet(Collections::emptyList)
        .stream()
        .map(Bucket::name)
        .toList();
  }

  @NotNull
  public List<Service> getServices(@NotNull String standName) throws StandsDaoException, StandNotFoundException {
    validator.validate(standName);

    Iterable<Result<Item>> standArtefacts = getStandArtefacts(standName);
    Collection<Item> lastModifiedArtefacts = getLastModifiedArtefacts(standArtefacts);
    Map<String, String> artefactUrls = getArtefactUrls(standName, lastModifiedArtefacts);
    return serviceListMapper.map(lastModifiedArtefacts, artefactUrls);
  }

  public boolean standExists(String standName) throws StandsDaoException {
    BucketExistsArgs bucketExistsArgs = BucketExistsArgs
        .builder()
        .bucket(standName)
        .build();

    try {
      return minioClient.bucketExists(bucketExistsArgs);
    } catch (Exception e) {
      throw new StandsDaoException(e);
    }
  }

  /**
   * Return a Map of artefact URLs
   *
   * @param standName a stand name
   * @param artefacts Collection of artefact info items
   * @return a map of artefact URLs, where the key is an artefact path like 'expectation/jlogic/00.01.01.json',
   * and the value is a string representation of artefact URL
   */
  private Map<String, String> getArtefactUrls(@NotNull String standName, @NotNull Collection<Item> artefacts) {
    validator.validate(standName);
    validator.validate(artefacts);

    return artefacts
        .stream()
        .collect(Collectors.toMap(
            this::getArtefactPath,
            artefact -> {
              try {
                return getArtefactUrl(standName, artefact);
              } catch (StandsDaoException e) {
                throw new RuntimeException(e);
              }
            }
        ));
  }

  @NotNull
  private String getArtefactUrl(String standName, Item artefact) throws StandsDaoException {
    Map<String, String> requestParams = new HashMap<String, String>();

    int urlExpirationPeriod = Integer.parseInt(minioProperties.getProperty("minio.artefact.url.expiration.period"));

    String artefactPath = getArtefactPath(artefact);
    requestParams.put("response-content-type", "application/json");

    try {
      return minioClient.getPresignedObjectUrl(
          GetPresignedObjectUrlArgs.builder()
              .method(Method.GET)
              .bucket(standName)
              .object(artefactPath)
              .expiry(urlExpirationPeriod, TimeUnit.HOURS)
              .extraQueryParams(requestParams)
              .build());
    } catch (Exception e) {
      throw new StandsDaoException("It is impossible to retrieve url for " + artefactPath + " in " + standName + " stand", e);
    }
  }

  @NotNull
  private Iterable<Result<Item>> getStandArtefacts(@NotNull String standName) throws StandsDaoException, StandNotFoundException {
    validator.validate(standName);

    if (!standExists(standName)) {
      throw new StandNotFoundException("Minio Storage does not contain '" + standName + "' stand");
    }

    try {
      ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
          .bucket(standName)
          .recursive(true)
          .build();
      return minioClient.listObjects(listObjectsArgs);
    } catch (Exception e) {
      throw new StandsDaoException("It is impossible to get object data from minio storage for " + standName + " stand", e);
    }
  }

  /**
   * Returns the last modified artefacts. If there are several artefact files were uploaded to Minio, the method returns Item object for
   * the last modified one.
   *
   * @param standArtefacts all items that represents different versions of artefacts. It means to one service as a consumer the collection can contain
   *                       several artefacts of different versions.
   * @return a collection of Item that will represent only one last modified artefact for a particular service as a consumer/producer. In case the
   * service is presented as a consumer and as a producer, it will be placed in the collection twice.
   * @throws StandsDaoException
   */
  @NotNull
  private Collection<Item> getLastModifiedArtefacts(@NotNull Iterable<Result<Item>> standArtefacts) throws StandsDaoException {
    validator.validate(standArtefacts);

    Map<String, Item> artefactMap = new HashMap<>();
    try {

      for (Result<Item> itemResult : standArtefacts) {
        Item currArtefact = itemResult.get();
        String artefactKey = extractArtefactKey(getArtefactPath(currArtefact));
        Item prevArtefact = artefactMap.putIfAbsent(artefactKey, currArtefact);

        if (prevArtefact != null) {

          if (currArtefact.lastModified().isAfter(prevArtefact.lastModified())) {
            artefactMap.put(artefactKey, currArtefact);
          }
        }
      }

    } catch (Exception e) {
      throw new StandsDaoException(e);
    }

    return artefactMap.values();
  }

  private String getArtefactPath(Item artefact) {

    return artefact.objectName();
  }

}

