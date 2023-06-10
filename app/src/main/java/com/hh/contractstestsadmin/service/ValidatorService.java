package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.validator.ValidationDto;
import com.hh.contractstestsadmin.exception.ValidatorException;
import java.io.IOException;
import java.io.InputStream;
import ru.hh.contract.validator.service.ContractsValidator;
import ru.hh.contract.validator.validation.OpenApiInteractionValidatorFactory;
import ru.hh.contract.validator.validation.ValidationContextProvider;

public class ValidatorService {

  private final ObjectMapper objectMapper;

  private final ValidationContextProvider validationContextProvider;

  private final OpenApiInteractionValidatorFactory openApiInteractionValidatorFactory;

  public ValidatorService(ObjectMapper objectMapper, ValidationContextProvider validationContextProvider,
      OpenApiInteractionValidatorFactory openApiInteractionValidatorFactory) {
    this.objectMapper = objectMapper;
    this.validationContextProvider = validationContextProvider;
    this.openApiInteractionValidatorFactory = openApiInteractionValidatorFactory;
  }

  public ValidationDto validate(String standName) throws ValidatorException, IOException {
    ContractsValidator contractsValidator = new ContractsValidator(validationContextProvider, openApiInteractionValidatorFactory);
    //contractsValidator.validateContracts();
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/full-validation-example.json");
    return objectMapper.readValue(inputStream, new TypeReference<>() {
    });
  }

}

