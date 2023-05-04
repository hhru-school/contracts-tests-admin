package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
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

public class ContractsDao {

  private final MinioClient minioClient;

  public ContractsDao(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  @NotNull
  public List<String> getStandNames() throws ContractsDaoException {
    List<Bucket> bucketList;
    try {
      bucketList = minioClient.listBuckets();
    } catch (Exception e) {
      throw new ContractsDaoException(e);
    }

    return ofNullable(bucketList)
        .orElseGet(Collections::emptyList)
        .stream()
        .map(Bucket::name)
        .toList();
  }

  @NotNull
  public List<Service> getServicesInfo(@NotNull String standName) throws ContractsDaoException, StandNotFoundException {
    Iterable<Result<Item>> bucketItems = getBucketItems(standName);
    return ServiceListMapper.map(bucketItems);
  }

  @NotNull
  private Iterable<Result<Item>> getBucketItems(@NotNull String bucketName) throws ContractsDaoException, StandNotFoundException {
    Iterable<Result<Item>> bucketItems;

    // We need this check as in case the bucket does not exist Minio return
    // Result<Item> with an error inside and 'bucketObjects' variable is not empty
    if (!isBucketExistent(bucketName)) {
      throw new StandNotFoundException("Minio Storage does not contain '" + bucketName + "' bucket");
    }

    try {
      ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
          .bucket(bucketName)
          .recursive(true)
          .build();
      bucketItems = minioClient.listObjects(listObjectsArgs);
    } catch (Exception e) {
      throw new ContractsDaoException(e);
    }

    return bucketItems;
  }

  private boolean isBucketExistent(String bucketName) throws ContractsDaoException {
    BucketExistsArgs bucketExistsArgs = BucketExistsArgs
        .builder()
        .bucket(bucketName)
        .build();

    try {
      if (minioClient.bucketExists(bucketExistsArgs)) {
        return true;
      }
    } catch (Exception e) {
      throw new ContractsDaoException(e);
    }

    return false;
  }
}

