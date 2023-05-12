package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.Validation;

import java.util.List;

public class StandValidationService {

  private final ContractsDao contractsDao;

  private final ValidationService validationService;

  public StandValidationService(ContractsDao contractsDao, ValidationService validationService) {
    this.contractsDao = contractsDao;
    this.validationService = validationService;
  }

  private void checkStandExistence(String standName) throws ContractsDaoException, StandNotFoundException {
    if (contractsDao.getStandNames().stream().noneMatch(s -> s.equals(standName))) {
      throw new StandNotFoundException("Stand '" + standName + "' does not exist");
    }
  }

  public List<ValidationPreviewDto> getValidationHistory(
      String standName,
      Integer sizeLimit
  ) throws ValidationHistoryNotFoundException, ContractsDaoException {
    try{
      checkStandExistence(standName);
    } catch (StandNotFoundException exception) {
      throw new ValidationHistoryNotFoundException("Validation history not found for stand '" + standName);
    }
    return validationService.getLatestValidationPreviews(standName, sizeLimit);
  }

  public void runValidation(String standName) throws StandNotFoundException, ContractsDaoException {
    checkStandExistence(standName);
    Validation validation = validationService.createValidation(standName);
  }
}
