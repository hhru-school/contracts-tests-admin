package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContractsDaoTest {

  @Test()
  void standNotFountExceptionIfStandNonExistent() {
    String bucketName = "non-existent-bucket-name";
    Throwable exception = assertThrows(StandNotFoundException.class, () -> {
      MinioClient minioClient = mock(MinioClient.class);

      BucketExistsArgs bucketExistsArgs = BucketExistsArgs
          .builder()
          .bucket(bucketName)
          .build();

      when(minioClient.bucketExists(bucketExistsArgs)).thenReturn(false);
      ContractsDao contractsDao = new ContractsDao("contracts-release", minioClient);
      Iterable<Result<Item>> servicesItems = contractsDao.getServicesInfo(bucketName);
    });
    assertEquals("Minio Storage does not contain '" + bucketName + "' bucket", exception.getMessage());
    assertEquals(StandNotFoundException.class, exception.getClass());
  }

  @Test()
  void serviceListIsEmptyIfMinioReturnsNull() {
    String bucketName = "test-bucket";
    MinioClient minioClient = mock(MinioClient.class);

    BucketExistsArgs bucketExistsArgs = BucketExistsArgs
        .builder()
        .bucket(bucketName)
        .build();

    try {
      when(minioClient.bucketExists(bucketExistsArgs)).thenReturn(true);
      List<Result<Item>> emptyList = Collections.emptyList();
    } catch (Exception e) {
      fail();
    }

    try {
      ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
          .bucket(bucketName)
          .recursive(true)
          .build();
      when(minioClient.listObjects(listObjectsArgs)).thenReturn(Collections.<Result<Item>>emptyList());
    } catch (Exception e) {
      fail();
    }

    ContractsDao contractsDao = new ContractsDao("contracts-release", minioClient);
    try {
      Iterable<Result<Item>> servicesList = contractsDao.getServicesInfo(bucketName);
      assertNotNull(servicesList);
      assertFalse(contractsDao.getServicesInfo(bucketName).iterator().hasNext());
    } catch (ContractsDaoException e) {
      fail();
    }
  }
}