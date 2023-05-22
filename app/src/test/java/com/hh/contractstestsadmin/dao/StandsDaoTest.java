package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dao.minio.mapper.ConsumerDataMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ProducerDataMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ServiceListMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ServiceMapper;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.model.artefacts.Service;
import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
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

      when(minioClient.bucketExists(any())).thenReturn(false);
      Properties properties = new Properties();
      properties.put("minio.consumer.artefact.type", "expectation");
      properties.put("minio.producer.artefact.type", "schema");
      StandsDao standsDao = new StandsDao(minioClient, properties, new ServiceListMapper(new ServiceMapper(properties, new ConsumerDataMapper(),
          new ProducerDataMapper()
      )));
      standsDao.getServices(bucketName);
    });
    assertEquals("Minio Storage does not contain '" + bucketName + "' stand", exception.getMessage());
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

    Properties properties = new Properties();
    properties.put("minio.consumer.artefact.type", "expectation");
    properties.put("minio.producer.artefact.type", "schema");
    StandsDao standsDao = new StandsDao(minioClient, properties, new ServiceListMapper(new ServiceMapper(properties, new ConsumerDataMapper(),
        new ProducerDataMapper()
    )));
    try {
      List<Service> servicesList = standsDao.getServices(bucketName);
      assertNotNull(servicesList);
      assertFalse(standsDao.getServices(bucketName).iterator().hasNext());
    } catch (StandsDaoException e) {
      fail();
    }
  }
}
