package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.dao.minio.mapper.ServiceListMapper;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.removeArtefactFilePostfix;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.Service;
import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.Optional.ofNullable;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

public class StandsDao {

  private final MinioClient minioClient;

  private final ServiceListMapper serviceListMapper;
  private final Validator validator;

  public StandsDao(MinioClient minioClient, ServiceListMapper serviceListMapper) {
    this.minioClient = minioClient;
    this.serviceListMapper = serviceListMapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
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
    Iterable<Result<Item>> standItems = getStandItems(standName);
    return serviceListMapper.map(getLastModifiedArtefacts(standItems));
  }

  @NotNull
  private Iterable<Result<Item>> getStandItems(@NotNull String standName) throws StandsDaoException, StandNotFoundException {
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
   * Returns the last modified artefacts. If there are several artefact files were uploaded to Minio, the method returns Item object for
   * the last modified one.
   *
   * @param standItems all items that represents different versions of artefacts. It means to one service as a consumer the collection can contain
   *                   several artefacts of different versions.
   * @return a collection of Item that will represent only one last modified artefact for a particular service as a consumer/producer. In case the
   * service is presented as a consumer and as a producer, it will be placed in the collection twice.
   * @throws StandsDaoException
   */
  @NotNull
  private Collection<Item> getLastModifiedArtefacts(Iterable<Result<Item>> standItems) throws StandsDaoException {
    Map<String, Item> itemMap = new HashMap<>();
    try {

      for (Result<Item> itemResult : standItems) {
        Item currItem = itemResult.get();
        String serviceTypeNameKey = removeArtefactFilePostfix(currItem.objectName());
        Item prevItem = itemMap.putIfAbsent(serviceTypeNameKey, currItem);

        if (prevItem != null) {

          if (currItem.lastModified().isAfter(prevItem.lastModified())) {
            itemMap.put(serviceTypeNameKey, currItem);
          }
        }
      }

    } catch (Exception e) {
      throw new StandsDaoException(e);
    }

    return itemMap.values();
  }
}

