package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.Validation;

import java.util.List;

public class StandValidationService {

  private final StandsDao standsDao;

  private final ValidationService validationService;

  public StandValidationService(StandsDao standsDao, ValidationService validationService) {
    this.standsDao = standsDao;
    this.validationService = validationService;
  }

  private void checkValidationHistoryExistence(String standName) throws StandsDaoException, ValidationHistoryNotFoundException {
    if (standsDao.getStandNames().stream().noneMatch(s -> s.equals(standName))) {
      throw new ValidationHistoryNotFoundException("Validation history not found because stand '" + standName + "' does not exist");
    }
  }

  public List<ValidationPreviewDto> getValidationHistory(
      String standName,
      Integer sizeLimit
  ) throws ValidationHistoryNotFoundException, StandsDaoException {
    checkValidationHistoryExistence(standName);
    return validationService.getLatestValidationPreviews(standName, sizeLimit);
  }

  public void runValidation(String standName) throws StandNotFoundException, StandsDaoException {
    checkValidationHistoryExistence(standName);
    Validation validation = validationService.createValidation(standName);
  }
}
