package com.hh.contractstestsadmin.dao;

import io.minio.MinioClient;
import io.minio.messages.Bucket;
import java.util.List;
import javax.inject.Inject;

public class ContractsDao {

  @Inject
  private MinioClient minioClient;

  public List<String> getStandNames() throws ContractsDaoException {
    List<String> result = null;

    List<Bucket> bucketList;
    try {
      bucketList = minioClient.listBuckets();
    } catch (Exception e) {
      throw new ContractsDaoException(e);
    }

    if (bucketList != null) {
      result = bucketList
          .stream()
          .map(Bucket::name)
          .toList();
    }

    return result;
  }

}
