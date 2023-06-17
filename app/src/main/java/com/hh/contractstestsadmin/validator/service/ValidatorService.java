package com.hh.contractstestsadmin.validator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.validator.dto.ValidationDto;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.ArtefactType;
import com.hh.contractstestsadmin.model.artefacts.Service;
import com.hh.contractstestsadmin.service.mapper.ExpectationsDataMapper;
import com.hh.contractstestsadmin.service.mapper.SchemaDataMapper;
import com.hh.contractstestsadmin.validator.mapper.ValidationResultMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  public ValidationDto validate(String standName) throws IOException, StandsDaoException {
    List<Service> standServices = standsDao.getServices(standName);

    Map<String, SchemaData> schemaDataMap = extractAndEnrichSchemas(standName, standServices);
    Map<String, ExpectationsData> expectationDataMap = extractAndEnrichExpectations(standName, standServices);

    List<ContractValidationResultDto> validationResults = contractsValidator.validateContracts(schemaDataMap, expectationDataMap);
    return ValidationResultMapper.map(validationResults, objectMapper);
  }

  private Map<String, ExpectationsData> extractAndEnrichExpectations(String standName, List<Service> standServices) {
    return standServices
        .stream()
        .filter(service -> service.getConsumerData().isPresent())
        .collect(Collectors.toMap(
            service -> service.getName(),
            service -> {
              try {
                return ExpectationsDataMapper.map(service,
                    standsDao.getArtefactBody(standName, standsDao.buildArtefactPath(standName, service.getName(), service.getVersion(),
                        ArtefactType.EXPECTATION)), objectMapper);
              } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
              }
            }
        ));
  }

  private Map<String, SchemaData> extractAndEnrichSchemas(String standName, List<Service> standServices) {
    return standServices
        .stream()
        .filter(service -> service.getProducerData().isPresent())
        .collect(Collectors.toMap(
            service -> service.getName(),
            service -> SchemaDataMapper.map(service,
                standsDao.getArtefactBody(standName, standsDao.buildArtefactPath(standName, service.getName(), service.getVersion(),
                    ArtefactType.SCHEMA)))));
  }

}

