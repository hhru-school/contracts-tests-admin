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

  private boolean standExist(String standName) throws ContractsDaoException, StandNotFoundException {
    return contractsDao.getStandNames().stream().anyMatch(s -> s.equals(standName));
  }

  public List<ValidationPreviewDto> getValidationHistory(
      String standName,
      Integer sizeLimit
  ) throws ValidationHistoryNotFoundException, ContractsDaoException {
    if(!standExist(standName)) {
      throw new ValidationHistoryNotFoundException("Validation history not found for stand '" + standName + "'");
    }
    return validationService.getLatestValidationPreviews(standName, sizeLimit);
  }

  public void runValidation(String standName) throws StandNotFoundException, ContractsDaoException {
    if(!standExist(standName)) {
      throw new StandNotFoundException("Stand '" + standName + "' not found");
    }
    Validation validation = validationService.createValidation(standName);
  }
}
