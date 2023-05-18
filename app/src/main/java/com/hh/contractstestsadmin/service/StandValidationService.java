package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dto.ServicesContainerDto;
import com.hh.contractstestsadmin.dto.ValidationServicesRelationsDto;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.Validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StandValidationService {

  private final ContractsDao contractsDao;

  private final ValidationService validationService;

  private final ObjectMapper objectMapper;

  public StandValidationService(ContractsDao contractsDao, ValidationService validationService, ObjectMapper objectMapper) {
    this.contractsDao = contractsDao;
    this.validationService = validationService;
    this.objectMapper = objectMapper;
  }

  private boolean standExistence(String standName) throws ContractsDaoException, StandNotFoundException {
    return contractsDao.getStandNames().stream().anyMatch(s -> s.equals(standName));
  }

  public List<ValidationPreviewDto> getValidationHistory(
      String standName,
      Integer sizeLimit
  ) throws ValidationHistoryNotFoundException, ContractsDaoException {
    if(standExistence(standName)) {
      throw new ValidationHistoryNotFoundException("Validation history not found for stand '" + standName);
    }
    return validationService.getLatestValidationPreviews(standName, sizeLimit);
  }

  public void runValidation(String standName) throws StandNotFoundException, ContractsDaoException {
    if(standExistence(standName)) {
      throw new StandNotFoundException("Stand '" + standName + "' not found");
    }
    Validation validation = validationService.createValidation(standName);
  }

  public ValidationServicesRelationsDto getValidationServicesRelations(String standName, Long validationId) throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/validation-exemple.json");
    ValidationServicesRelationsDto validationServicesRelationsDto = objectMapper.readValue(inputStream, ValidationServicesRelationsDto.class);
    validationServicesRelationsDto.setId(validationId);
    return validationServicesRelationsDto;
  }
}
