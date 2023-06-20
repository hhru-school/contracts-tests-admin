package com.hh.contractstestsadmin.validator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.exception.MinioClientException;
import com.hh.contractstestsadmin.validator.dto.ValidationDto;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.ArtefactType;
import com.hh.contractstestsadmin.model.artefacts.Service;
import com.hh.contractstestsadmin.validator.dto.WrongExpectationDto;
import com.hh.contractstestsadmin.validator.mapper.ExpectationsDataMapper;
import com.hh.contractstestsadmin.validator.mapper.SchemaDataMapper;
import com.hh.contractstestsadmin.validator.mapper.ValidationResultMapper;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.contract.validator.dto.ContractValidationResultDto;
import ru.hh.contract.validator.service.ContractsValidator;
import ru.hh.contract.validator.service.ExpectationsData;
import ru.hh.contract.validator.service.SchemaData;

public class ValidatorService {

  private final StandsDao standsDao;

  private final ObjectMapper objectMapper;

  private final ContractsValidator contractsValidator;

  private final String minioReleaseName;

  private static final Logger LOG = LoggerFactory.getLogger(ValidatorService.class);

  public ValidatorService(
      StandsDao standsDao, ObjectMapper objectMapper, ContractsValidator contractsValidator, String minioReleaseName
  ) {
    this.standsDao = standsDao;
    this.objectMapper = objectMapper;
    this.contractsValidator = contractsValidator;
    this.minioReleaseName = minioReleaseName;
  }

  public ValidationDto validate(String standName) {
    try {
      List<ServiceWithStand> services = extractServicesForValidation(standName);

      Map<String, SchemaData> serviceNameToSchema = extractAndEnrichSchemas(services);
      Map<String, ExpectationsData> serviceNameToExpectation = extractAndEnrichExpectations(services);

      List<ContractValidationResultDto> validationResults = contractsValidator.validateContracts(serviceNameToSchema, serviceNameToExpectation);

      ValidationDto validation = ValidationResultMapper.map(validationResults, objectMapper);
      enrichValidation(validation, services);
      return validation;
    } catch (StandsDaoException e) {
      LOG.error("Unable to get data from minio", e);
      throw new RuntimeException(e);
    } catch (JsonProcessingException e) {
      LOG.error("Unable to parse validation result", e);
      throw new RuntimeException(e);
    }
  }

  public List<ServiceWithStand> extractServicesForValidation(String standName) throws StandsDaoException {
    List<ServiceWithStand> releaseServices = standsDao.getServices(minioReleaseName)
        .stream()
        .map(service -> new ServiceWithStand(service, minioReleaseName))
        .toList();
    List<ServiceWithStand> standServices = Collections.emptyList();
    if (!minioReleaseName.equals(standName)) {
      standServices = standsDao.getServices(standName)
          .stream()
          .map(service -> new ServiceWithStand(service, standName))
          .toList();
    }
    return Stream.of(releaseServices, standServices)
        .flatMap(Collection::stream)
        .collect(Collectors.toMap(
            serviceWithStand -> serviceWithStand.service().getName(),
            Function.identity(),
            this::compareServices
        ))
        .values()
        .stream()
        .toList();
  }

  private ServiceWithStand compareServices(ServiceWithStand service1, ServiceWithStand service2) {
    ComparableVersion service1Version = new ComparableVersion(service1.service().getVersion());
    ComparableVersion service2Version = new ComparableVersion(service2.service().getVersion());
    if (service1Version.compareTo(service2Version) > 0) {
      return service1;
    }
    return service2;
  }

  public void enrichValidation(ValidationDto validation, List<ServiceWithStand> services) {
    Map<String, String> serviceNameToStandName = services.stream()
        .collect(Collectors.toMap(
            serviceWithStand -> serviceWithStand.service().getName(),
            ServiceWithStand::standName
        ));
    for (WrongExpectationDto wrongExpectation: validation.getWrongExpectations()){
      String consumerStandName = serviceNameToStandName.get(wrongExpectation.getConsumerName());
      String producerStandName = serviceNameToStandName.get(wrongExpectation.getProducerName());
      wrongExpectation.setConsumerIsRelease(consumerStandName.equals(minioReleaseName));
      wrongExpectation.setProducerIsRelease(producerStandName.equals(minioReleaseName));
    }
  }

  private Map<String, ExpectationsData> extractAndEnrichExpectations(List<ServiceWithStand> services) {
    return services.stream()
        .filter(serviceWithStand -> serviceWithStand.service().getConsumerData().isPresent())
        .collect(Collectors.toMap(
            serviceWithStand -> serviceWithStand.service().getName(),
            this::extractAndEnrichExpectation
        ));
  }

  private Map<String, SchemaData> extractAndEnrichSchemas(List<ServiceWithStand> services) {
    return services.stream()
        .filter(serviceWithStand -> serviceWithStand.service().getProducerData().isPresent())
        .collect(Collectors.toMap(
            serviceWithStand -> serviceWithStand.service().getName(),
            this::extractAndEnrichSchema
        ));
  }

  private ExpectationsData extractAndEnrichExpectation(ServiceWithStand serviceWithStand) {
    try {
      String expectationsString = standsDao.getArtefactContent(
          serviceWithStand.standName(),
          standsDao.buildArtefactPath(
              serviceWithStand.service().getName(),
              serviceWithStand.service().getVersion(),
              ArtefactType.EXPECTATION
          )
      );
      return ExpectationsDataMapper.map(serviceWithStand.service(), expectationsString, objectMapper);
    } catch (JsonProcessingException e) {
      LOG.error("Unable to parse the expectation file", e);
      throw new RuntimeException(e);
    } catch (MinioClientException | StandsDaoException e) {
      LOG.error("Unable to get expectation file from minio", e);
      throw new RuntimeException(e);
    }
  }

  private SchemaData extractAndEnrichSchema(ServiceWithStand serviceWithStand) {
    try {
      String schemaString = standsDao.getArtefactContent(
          serviceWithStand.standName(),
          standsDao.buildArtefactPath(
              serviceWithStand.service().getName(),
              serviceWithStand.service().getVersion(),
              ArtefactType.SCHEMA
          )
      );
      return SchemaDataMapper.map(serviceWithStand.service(), schemaString);
    } catch (MinioClientException | StandsDaoException e) {
      LOG.error("Unable to get schema file from minio", e);
      throw new RuntimeException(e);
    }
  }

  private record ServiceWithStand(Service service, String standName) {
  }

}
