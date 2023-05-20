package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dto.ExpectationDto;
import com.hh.contractstestsadmin.dto.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class StandValidationService {

  private final ContractsDao contractsDao;

  private final ValidationService validationService;

  private final ObjectMapper objectMapper;

  public StandValidationService(ContractsDao contractsDao, ValidationService validationService, ObjectMapper objectMapper) {
    this.contractsDao = contractsDao;
    this.validationService = validationService;
    this.objectMapper = objectMapper;
  }

  private boolean standExists(String standName) throws ContractsDaoException, StandNotFoundException {
    return contractsDao.getStandNames().stream().anyMatch(s -> s.equals(standName));
  }

  public List<ValidationPreviewDto> getValidationHistory(
      String standName,
      Integer sizeLimit
  ) throws ValidationHistoryNotFoundException, ContractsDaoException {
    if (!standExists(standName)) {
      throw new ValidationHistoryNotFoundException("Validation history not found for stand '" + standName + "'");
    }
    return validationService.getLatestValidationPreviews(standName, sizeLimit);
  }

  public void runValidation(String standName) throws StandNotFoundException, ContractsDaoException {
    if (!standExists(standName)) {
      throw new StandNotFoundException("Stand '" + standName + "' not found");
    }
    Validation validation = validationService.createValidation(standName);
  }

  public ValidationWithRelationsDto getValidationWithRelations(String standName, Long validationId) throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/validation-with-relations-example.json");
    ValidationWithRelationsDto validationWithRelationsDto = objectMapper.readValue(inputStream, ValidationWithRelationsDto.class);
    validationWithRelationsDto.setId(validationId);
    return validationWithRelationsDto;
  }

  public List<ExpectationDto> getExpectations(String standName, Long validationId, Long producerId, Long consumerId) throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/expectations-example.json");
    return objectMapper.readValue(inputStream, new TypeReference<>() {
    });
  }

  public String getValidatorError(String standName, Long validationId) {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/validator-error-example.json");
    if (inputStream == null) {
      return "";
    }
    return new BufferedReader(new InputStreamReader(inputStream))
        .lines().collect(Collectors.joining("\n"));
  }
}
