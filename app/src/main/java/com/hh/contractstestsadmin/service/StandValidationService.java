package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.api.ValidationPreviewDto;
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

  private boolean standExists(String standName) throws StandsDaoException, StandNotFoundException {
    return standsDao.getStandNames().stream().anyMatch(s -> s.equals(standName));
  }

  public List<ValidationPreviewDto> getValidationHistory(
      String standName,
      Integer sizeLimit
  ) throws ValidationHistoryNotFoundException, StandsDaoException {
    if(!standExists(standName)) {
      throw new ValidationHistoryNotFoundException("Validation history not found for stand '" + standName + "'");
    }
    return validationService.getLatestValidationPreviews(standName, sizeLimit);
  }

  public void runValidation(String standName) throws StandNotFoundException, StandsDaoException {
    if(!standExists(standName)) {
      throw new StandNotFoundException("Stand '" + standName + "' not found");
    }
    Validation validation = validationService.createValidation(standName);
  }
}
