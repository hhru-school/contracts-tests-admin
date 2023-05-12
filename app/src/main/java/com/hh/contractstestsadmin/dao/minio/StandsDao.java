package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.dao.minio.mapper.ServiceListMapper;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.Service;
import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import java.util.Collections;
import java.util.List;
import static java.util.Optional.ofNullable;
import javax.validation.constraints.NotNull;

public class StandsDao {

  private final MinioClient minioClient;

  private final ServiceListMapper serviceListMapper;

  public StandsDao(MinioClient minioClient, ServiceListMapper serviceListMapper) {
    this.minioClient = minioClient;
    this.serviceListMapper = serviceListMapper;
  }

  @NotNull
  public List<String> getStandNames() throws StandsDaoException {
    List<Bucket> bucketList;
    try {
      bucketList = minioClient.listBuckets();
    } catch (Exception e) {
      throw new StandsDaoException(e);
    }

    return ofNullable(bucketList)
        .orElseGet(Collections::emptyList)
        .stream()
        .map(Bucket::name)
        .toList();
  }

  @NotNull
  public List<Service> getServices(@NotNull String standName) throws StandsDaoException, StandNotFoundException {
    Iterable<Result<Item>> bucketItems = getBucketItems(standName);
    return serviceListMapper.map(bucketItems);
  }

  @NotNull
  private Iterable<Result<Item>> getBucketItems(@NotNull String bucketName) throws StandsDaoException, StandNotFoundException {
    Iterable<Result<Item>> bucketItems;

    // We need this check as in case the bucket does not exist Minio return
    // Result<Item> with an error inside and 'bucketObjects' variable is not empty
    if (!bucketExists(bucketName)) {
      throw new StandNotFoundException("Minio Storage does not contain '" + bucketName + "' bucket");
    }

    try {
      ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
          .bucket(bucketName)
          .recursive(true)
          .build();
      bucketItems = minioClient.listObjects(listObjectsArgs);
    } catch (Exception e) {
      throw new StandsDaoException(e);
    }

    return bucketItems;
  }

  private boolean bucketExists(String bucketName) throws StandsDaoException {
    BucketExistsArgs bucketExistsArgs = BucketExistsArgs
        .builder()
        .bucket(bucketName)
        .build();

    try {
      if (minioClient.bucketExists(bucketExistsArgs)) {
        return true;
      }
    } catch (Exception e) {
      throw new StandsDaoException(e);
    }

    return false;
  }
}

