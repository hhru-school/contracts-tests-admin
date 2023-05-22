package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.dao.minio.mapper.ServiceListMapper;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractArtefactKey;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractArtefactVersion;
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
  private static final int DEFAULT_URL_EXPIRED_PERIOD = 7;

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
    Collection<Item> lastModifiedArtefacts = getArtefactsOfLastVersions(standArtefacts);
    return serviceListMapper.map(lastModifiedArtefacts);
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

  public String getArtefactUrl(String standName, String filePath) throws StandsDaoException {
    Map<String, String> requestParams = new HashMap<>();

    int urlExpirationPeriod = getUrlExpirationPeriod();
    requestParams.put("response-content-type", "application/json");

    try {
      return minioClient.getPresignedObjectUrl(
          GetPresignedObjectUrlArgs.builder()
              .method(Method.GET)
              .bucket(standName)
              .object(filePath)
              .expiry(urlExpirationPeriod, TimeUnit.DAYS)
              .extraQueryParams(requestParams)
              .build());
    } catch (Exception e) {
      throw new StandsDaoException("It is impossible to retrieve url for " + filePath + " in " + standName + " stand", e);
    }
  }

  private int getUrlExpirationPeriod() {
    try {
      return Integer.parseInt(minioProperties.getProperty("minio.artefact.url.expiration.period"));
    } catch (NumberFormatException e) {
      return DEFAULT_URL_EXPIRED_PERIOD;
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
   * Returns an artefact of the last version. If there are several artefact files were uploaded to Minio, the method returns Item object for
   * the last artefact version.
   *
   * @param standArtefacts all items that represents artefacts of several versions. It means to one service as a consumer the collection can contain
   *                       several artefacts of different versions.
   * @return a collection of Item that will represent only one last version artefact for a particular service as a consumer/producer. In case the
   * service is presented as a consumer and as a producer, it will be placed in the collection twice.
   * @throws StandsDaoException
   */
  @NotNull
  private Collection<Item> getArtefactsOfLastVersions(@NotNull Iterable<Result<Item>> standArtefacts) throws StandsDaoException {
    validator.validate(standArtefacts);

    Map<String, Item> artefactMap = new HashMap<>();
    try {

      for (Result<Item> itemResult : standArtefacts) {
        Item currArtefact = itemResult.get();
        String artefactKey = extractArtefactKey(getArtefactPath(currArtefact));
        Item prevArtefact = artefactMap.putIfAbsent(artefactKey, currArtefact);

        if (prevArtefact != null) {

          int compareResult = getArtefactVersion(currArtefact).compareToIgnoreCase(getArtefactVersion(prevArtefact));
          boolean currArtefactIsNewer = (compareResult > 0);

          if (currArtefactIsNewer) {
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

  private String getArtefactVersion(Item artefact) {

    return extractArtefactVersion(getArtefactPath(artefact));
  }

}

