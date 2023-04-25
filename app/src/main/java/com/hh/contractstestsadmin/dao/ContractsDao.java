package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.exception.ContractsDaoException;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import java.util.Collections;
import java.util.List;
import static java.util.Optional.ofNullable;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

public class ContractsDao {

  @Inject
  private MinioClient minioClient;

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

}
