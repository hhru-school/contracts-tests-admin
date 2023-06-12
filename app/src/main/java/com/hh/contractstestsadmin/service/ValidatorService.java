package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.validator.ValidationDto;
import com.hh.contractstestsadmin.dto.validator.WrongExpectationDto;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import ru.hh.contract.validator.dto.ContractValidationResultDto;
import ru.hh.contract.validator.service.ContractsValidator;
import ru.hh.contract.validator.service.ExpectationsData;
import ru.hh.contract.validator.service.SchemaData;

public class ValidatorService {

  private final StandsDao standsDao;

  private final ObjectMapper objectMapper;

  private final ContractsValidator contractsValidator;

  public ValidatorService(
      StandsDao standsDao, ObjectMapper objectMapper, ContractsValidator contractsValidator
  ) {
    this.standsDao = standsDao;
    this.objectMapper = objectMapper;
    this.contractsValidator = contractsValidator;
  }

  public ValidationDto validate(String standName) throws IOException {
    Map<String, SchemaData> schemaDataMap = standsDao.getSchemaDataMap(standName);
    Map<String, ExpectationsData> expectationDataMap = standsDao.getExpectationsDataMap(standName);

    List<ContractValidationResultDto> validationResults = contractsValidator.validateContracts(schemaDataMap, expectationDataMap);

    OutputStream outputStream = new ByteArrayOutputStream();
    objectMapper.writeValue(outputStream, validationResults);
    List<WrongExpectationDto> wrongExpectationDtos = objectMapper.readValue(outputStream.toString(), new TypeReference<>() {
    });
    ValidationDto validationDto = new ValidationDto();
    validationDto.setValidatorReport(objectMapper.writeValueAsString(validationResults));
    validationDto.setWrongExpectationsDto(wrongExpectationDtos);

    return validationDto;
  }
}

