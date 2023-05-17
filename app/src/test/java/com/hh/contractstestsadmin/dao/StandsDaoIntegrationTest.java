package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dao.minio.mapper.ConsumerDataMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ProducerDataMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ServiceListMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ServiceMapper;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.Service;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import java.util.List;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The test will be workable in case the Minio Storage has already been deployed.
 * So it will be possible after the application was deployed from docker.
 */
class StandsDaoIntegrationTest {

  private static MinioClient minioClient;

  @BeforeAll
  static void setup() {
    minioClient = connect();
  }

  @Test
  void dataCheck() {
    try {
      assertNotNull(minioClient);
      List<Bucket> blist = minioClient.listBuckets();
      assertNotNull(blist);
      assertTrue(blist.size() > 0);
    } catch (MinioException e) {
      System.out.println("Connection failed: " + e.getMessage());
      fail();
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void getServicesTest() throws StandsDaoException {
    Properties properties = new Properties();
    properties.put("minio.consumer.artefact.type", "expectation");
    properties.put("minio.producer.artefact.type", "schema");
    properties.put("minio.artefact.url.expiration.period", "24");
    StandsDao standsDao = new StandsDao(minioClient, properties, new ServiceListMapper(properties, new ServiceMapper(properties, new ConsumerDataMapper(),
        new ProducerDataMapper())));
    List<Service> servicesList = standsDao.getServices("contract-release");
    assertEquals(servicesList.size(), 2);
  }

  private static MinioClient connect() {
    MinioClient minioClient = MinioClient.builder()
        .endpoint("http://localhost:9000")
        .credentials("hhtech", "hhtech123")
        .build();

    return minioClient;
  }

  @Test
  public void getServicesForNullStand() {
    Properties properties = new Properties();
    properties.put("minio.consumer.artefact.type", "expectation");
    properties.put("minio.producer.artefact.type", "schema");
    properties.put("minio.artefact.url.expiration.period", "24");

    try {
      assertNotNull(minioClient);
      StandsDao standsDao = new StandsDao(minioClient, properties, new ServiceListMapper(properties, new ServiceMapper(new Properties(),
          new ConsumerDataMapper(), new ProducerDataMapper())));
      standsDao.getServices(null);
    } catch (StandsDaoException e) {
      e.printStackTrace();
      fail();
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

}
