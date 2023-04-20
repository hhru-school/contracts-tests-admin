package com.hh.contractstestsadmin.dao;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The test will be workable in case the Minio Storage has already been deployed.
 * So it will be possible after the application was deployed from docker.
 */
class ContractsDaoTest {

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

  private static MinioClient connect() {
    MinioClient minioClient = MinioClient.builder()
        .endpoint("http://minio:9000")
        .credentials("hhtech", "hhtech123")
        .build();

    return minioClient;
  }

}
