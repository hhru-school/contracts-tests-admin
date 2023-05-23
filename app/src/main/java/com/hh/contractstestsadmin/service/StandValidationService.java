package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.api.ExpectationDto;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.api.ValidationPreviewDto;
import com.hh.contractstestsadmin.dto.validator.ValidationDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationRecordException;
import com.hh.contractstestsadmin.exception.ValidatorException;
import com.hh.contractstestsadmin.model.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class StandValidationService {

  private final StandsDao standsDao;

  private final ValidationService validationService;

  private final ObjectMapper objectMapper;

  private final ExecutorService executorService;

  private final ValidatorService validatorService;

  public StandValidationService(
      StandsDao standsDao,
      ValidationService validationService,
      ExecutorService executorService,
      ValidatorService validatorService,
      ObjectMapper objectMapper
  ) {
    this.standsDao = standsDao;
    this.validationService = validationService;
    this.executorService = executorService;
    this.validatorService = validatorService;
    this.objectMapper = objectMapper;
  }

  private boolean standExists(String standName) throws StandsDaoException, StandNotFoundException {
    return standsDao.getStandNames().stream().anyMatch(s -> s.equals(standName));
  }

  public List<ValidationPreviewDto> getValidationHistory(
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
    executorService.submit(() -> startValidationProcess(standName, validation.getId()));
  }

  private void startValidationProcess(String standName, Long validationId) {
    try {
      ValidationDto validationDto = validatorService.validate(standName);
      validationService.recordValidation(validationId, validationDto);
    } catch (ValidatorException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ValidationRecordException e) {
      throw new RuntimeException(e);
    }
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
