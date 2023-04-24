package com.hh.contractstestsadmin.dao;

import io.minio.MinioClient;
import io.minio.messages.Bucket;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import javax.inject.Inject;

public class ContractsDao {

  @Inject
  private MinioClient minioClient;

  public void setMinioClient(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  public Optional<List<String>> getStandNames() throws ContractsDaoException {
    List<Bucket> bucketList;
    try {
      bucketList = minioClient.listBuckets();
    } catch (Exception e) {
      throw new ContractsDaoException(e);
    }

    return of(ofNullable(bucketList)
        .orElseGet(Collections::emptyList)
        .stream()
        .map(Bucket::name)
        .toList());
  }

}
