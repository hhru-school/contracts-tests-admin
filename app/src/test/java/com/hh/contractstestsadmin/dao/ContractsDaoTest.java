package com.hh.contractstestsadmin.dao;

import io.minio.MinioClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for ContractsDao
 */
public class ContractsDaoTest {

  private static final Logger logger = LoggerFactory.getLogger(ContractsDaoTest.class);

  @Test
  void isPossibleToGetStandNamesWhenMinioReturnsNull() {
    try {
      MinioClient minioClient = mock(MinioClient.class);
      when(minioClient.listBuckets()).thenReturn(null);
      ContractsDao contractsDao = new ContractsDao();
      contractsDao.setMinioClient(minioClient);
      Optional<List<String>> standNamesOpt = contractsDao.getStandNames();
      boolean standNamesOptEmpty = standNamesOpt.isEmpty();
      boolean standNamesOptContainsEmptyList = standNamesOpt.isPresent() && standNamesOpt.get().size() == 0;
      assertTrue(standNamesOptEmpty || standNamesOptContainsEmptyList);
    } catch (Exception e) {
      logger.error("The test preconditions are failed", e);
      fail();
    }
  }

  @Test
  void isPossibleToGetStandNamesWhenMinioReturnsEmpty() {
    try {
      MinioClient minioClient = mock(MinioClient.class);
      when(minioClient.listBuckets()).thenAnswer(a -> new ArrayList<String>());
      ContractsDao contractsDao = new ContractsDao();
      contractsDao.setMinioClient(minioClient);
      Optional<List<String>> standNamesOpt = contractsDao.getStandNames();
      boolean standNamesOptEmpty = standNamesOpt.isEmpty();
      boolean standNamesOptContainsEmptyList = standNamesOpt.isPresent() && standNamesOpt.get().size() == 0;
      assertTrue(standNamesOptEmpty || standNamesOptContainsEmptyList);
    } catch (Exception e) {
      logger.error("The test preconditions are failed", e);
      fail();
    }
  }
}
