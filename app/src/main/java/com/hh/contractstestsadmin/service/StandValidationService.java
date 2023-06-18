package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.api.ExpectationDto;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;

import com.hh.contractstestsadmin.model.Validation;
import com.hh.contractstestsadmin.resource.CustomEntityResource;
import com.hh.contractstestsadmin.validator.dto.ValidationDto;
import com.hh.contractstestsadmin.validator.service.ValidatorService;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class StandValidationService {

  private static final Logger LOG = LoggerFactory.getLogger(CustomEntityResource.class);

  private final StandsDao standsDao;

  private final ValidationService validationService;

  private final TaskExecutor taskExecutor;

  private final ValidatorService validatorService;

  public StandValidationService(
      StandsDao standsDao,
      ValidationService validationService,
      TaskExecutor taskExecutor,
      ValidatorService validatorService
  ) {
    this.standsDao = standsDao;
    this.validationService = validationService;
    this.taskExecutor = taskExecutor;
    this.validatorService = validatorService;
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
    taskExecutor.execute(() -> startValidationProcess(standName, validation.getId()));
  }

  private void startValidationProcess(String standName, Long validationId) {
    try {
      ValidationDto validationResult = validatorService.validate(standName);
      validationService.recordValidationResult(validationId, validationResult);
    } catch (Exception e) {
      LOG.error("validation process ended with an error", e);
      throw new RuntimeException(e);
    }
  }

  public ValidationWithRelationsDto getValidationWithRelations(String standName, Long validationId) {
    return validationService.getServiceRelation(validationId, standName);
  }

  public List<ExpectationDto> getExpectations(String standName, Long validationId, Long producerId, Long consumerId) throws StandsDaoException {
    if (!standExists(standName)) {
      throw new StandNotFoundException("not found stand with name: " + standName);
    }
    return validationService.getExpectations(standName, validationId, producerId, consumerId);
  }

  public String getValidatorReport(String standName, Long validationId) throws StandsDaoException {
    if (!standExists(standName)) {
      throw new StandNotFoundException("not found stand with name: " + standName);
    }
    return validationService.getValidationReport(standName, validationId);
  }
}
