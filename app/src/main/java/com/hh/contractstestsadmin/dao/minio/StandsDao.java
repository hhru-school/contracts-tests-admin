package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.dao.minio.mapper.ServiceListMapper;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractArtefactKey;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractArtefactVersion;
import com.hh.contractstestsadmin.exception.MinioClientException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.ArtefactType;
import com.hh.contractstestsadmin.model.artefacts.Service;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
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
import static javax.validation.Validation.buildDefaultValidatorFactory;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import okhttp3.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.contract.validator.service.ExpectationsData;
import ru.hh.contract.validator.service.SchemaData;

public class StandsDao {

  private final MinioClient minioClient;
  private final ServiceListMapper serviceListMapper;
  private final Validator validator;
  private final Properties minioProperties;
  private static final Logger log = LoggerFactory.getLogger(StandsDao.class);

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

    return serviceListMapper.map(lastModifiedArtefacts, standName);
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

  public boolean isArtefactPath(String standName, String artefactPath) throws StandsDaoException, MinioClientException {
    StatObjectArgs artefactExists = StatObjectArgs
        .builder()
        .bucket(standName)
        .object(artefactPath)
        .build();

    try {
      return minioClient.statObject(artefactExists) != null;
    } catch (ErrorResponseException e) {
      int statusCode = getStatusCode(e.response());
      throw new MinioClientException(e.getMessage(), e, statusCode);
    } catch (Exception e) {
      throw new StandsDaoException(e);
    }
  }

  @NotNull
  public String getArtefactUrl(String standName, String artefactPath) throws StandsDaoException, MinioClientException {
    Map<String, String> requestParams = new HashMap<String, String>();
    boolean artefactPathExist = isArtefactPath(standName, artefactPath);
    if (!artefactPathExist) {
      throw new MinioClientException("not found file from path " + artefactPath, HttpStatus.NOT_FOUND_404);
    }

    int urlExpirationPeriod = Integer.parseInt(minioProperties.getProperty("minio.artefact.url.expiration.period"));
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
    } catch (ErrorResponseException e) {
      int statusCode = getStatusCode(e.response());
      throw new MinioClientException(e.getMessage(), e, statusCode);
    } catch (Exception e) {
      throw new StandsDaoException("It is impossible to retrieve url for " + artefactPath + " in " + standName + " stand", e);
    }
  }

  private static int getStatusCode(Response response) {
    if (response == null) {
      log.warn("response is null");
      return HttpStatus.INTERNAL_SERVER_ERROR_500;
    }
    return response.code();
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

  public String getBaseMinioUrl() {
    return minioProperties.getProperty("minio.base.url");
  }

  public String getExternalMinioUrl() {
    return minioProperties.getProperty("minio.external.base.url");
  }

  public String buildArtefactPath(String standName, String serviceName, String version, ArtefactType artefactType){
    if(artefactType.equals(ArtefactType.EXPECTATION)){
      return standName + "/" + minioProperties.getProperty("minio.consumer.artefact.type") + "/" + serviceName + "/" + version + ".json";
    }
    return standName + "/" + minioProperties.getProperty("minio.producer.artefact.type") + "/" + serviceName + "/" + version + ".yaml";
  }

  public String getArtefactBody(String standName, String artefactPath) {
    return "";
  }
}
