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
    List<Bucket> standList;
    try {
      standList = minioClient.listBuckets();
    } catch (Exception e) {
      throw new StandsDaoException(e);
    }

    return ofNullable(standList)
        .orElseGet(Collections::emptyList)
        .stream()
        .map(Bucket::name)
        .toList();
  }

  @NotNull
  public List<Service> getServices(@NotNull String standName) throws StandsDaoException, StandNotFoundException {
    Iterable<Result<Item>> standItems = getStandItems(standName);
    return serviceListMapper.map(standItems);
  }

  @NotNull
  private Iterable<Result<Item>> getStandItems(@NotNull String standName) throws StandsDaoException, StandNotFoundException {
    Iterable<Result<Item>> standItems;

    if (!standExists(standName)) {
      throw new StandNotFoundException("Minio Storage does not contain '" + standName + "' stand");
    }

    try {
      ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
          .bucket(standName)
          .recursive(true)
          .build();
      standItems = minioClient.listObjects(listObjectsArgs);
    } catch (Exception e) {
      throw new StandsDaoException(e);
    }

    return standItems;
  }

  public boolean standExists(String standName) throws StandsDaoException {
    BucketExistsArgs bucketExistsArgs = BucketExistsArgs
        .builder()
        .bucket(standName)
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

