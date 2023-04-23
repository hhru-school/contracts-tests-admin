package com.hh.contractstestsadmin.dao;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import java.util.List;
import javax.inject.Inject;
import org.junit.Ignore;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * The test will be workable in case the Minio Storage has already been deployed.
 * So it will be possible after the application was deployed from docker.
 * Needs to be reimplemented with TestContainers usage to make possible running tests
 * during build process in docker
 */
@SpringBootTest
class ContractsDaoTest {

  Logger logger = LoggerFactory.getLogger(ContractsDaoTest.class);

  @Inject
  MinioClient minioClient;

  @Inject
  ContractsDao contractsDao;

  //@Test //The test is switched off as it need to be reimplemented with testcontainers
  void getDataFromMinioStorage() {
    try {
      assertNotNull(minioClient);
      List<Bucket> blist = minioClient.listBuckets();
      assertNotNull(blist);
      assertTrue(blist.size() > 0);
    } catch (MinioException e) {
      logger.error(e.getMessage(), e);
      fail();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      fail();
    }
  }

  //@Test //The test is switched off as it need to be reimplemented with testcontainers
  void getStandNames() throws ContractsDaoException {
    List<String> standNames = contractsDao.getStandNames();
    assertNotNull(standNames);
    assertTrue(standNames.size() > 0);
  }

}
