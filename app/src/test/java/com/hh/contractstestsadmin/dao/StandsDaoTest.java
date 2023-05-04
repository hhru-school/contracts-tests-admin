package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.model.Service;
import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
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

class StandsDaoTest {

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
      StandsDao standsDao = new StandsDao(minioClient);
      standsDao.getServicesInfo(bucketName);
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
    } catch (Exception e) {
      fail();
    }

    try {
      ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
          .bucket(bucketName)
          .recursive(true)
          .build();
      when(minioClient.listObjects(listObjectsArgs)).thenReturn(Collections.emptyList());
    } catch (Exception e) {
      fail();
    }

    StandsDao standsDao = new StandsDao(minioClient);
    try {
      List<Service> servicesList = standsDao.getServicesInfo(bucketName);
      assertNotNull(servicesList);
      assertFalse(standsDao.getServicesInfo(bucketName).iterator().hasNext());
    } catch (StandsDaoException e) {
      fail();
    }
  }
}