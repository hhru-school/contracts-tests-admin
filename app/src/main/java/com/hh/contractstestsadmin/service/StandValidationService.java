package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.api.ExpectationDto;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class StandValidationService {

  private final StandsDao standsDao;

  private final ValidationService validationService;

  public StandValidationService(StandsDao standsDao, ValidationService validationService) {
    this.standsDao = standsDao;
    this.validationService = validationService;
  }

  private boolean standExists(String standName) throws StandsDaoException, StandNotFoundException {
    return standsDao.getStandNames().stream().anyMatch(s -> s.equals(standName));
  }

  public List<ValidationMetaInfoDto> getValidationHistory(
      String standName,
      Integer sizeLimit
  ) throws ValidationHistoryNotFoundException, StandsDaoException {
    if (!standExists(standName)) {
      throw new ValidationHistoryNotFoundException("Validation history not found for stand '" + standName + "'");
    }
    return validationService.getLatestValidationPreviews(standName, sizeLimit);
  }

  public void runValidation(String standName) throws StandNotFoundException, StandsDaoException {
    if (!standExists(standName)) {
      throw new StandNotFoundException("Stand '" + standName + "' not found");
    }
    Validation validation = validationService.createValidation(standName);
  }

  public ValidationWithRelationsDto getValidationWithRelations(String standName, Long validationId) {
    return validationService.getServiceRelation(validationId, standName);
  }

  public List<ExpectationDto> getExpectations(String standName, Long validationId, Long producerId, Long consumerId) throws IOException {
    return validationService.getExpectations(standName, validationId, producerId, consumerId);
  }

  public String getValidatorReport(String standName, Long validationId) {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/validator-report-example.json");
    if (inputStream == null) {
      return "";
    }
    return new BufferedReader(new InputStreamReader(inputStream))
        .lines().collect(Collectors.joining("\n"));
  }
}
