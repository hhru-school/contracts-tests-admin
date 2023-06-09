package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ErrorTypeDao;
import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dao.ServiceDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dao.ValidationInfoDao;
import com.hh.contractstestsadmin.dto.api.ExpectationDto;
import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.exception.ServiceNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ErrorLevel;
import com.hh.contractstestsadmin.model.ErrorType;
import com.hh.contractstestsadmin.model.Expectation;
import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.exception.ValidationResultRecordException;

import com.hh.contractstestsadmin.exception.ValidationResultRecordException;
import com.hh.contractstestsadmin.model.ServiceType;
import com.hh.contractstestsadmin.model.Validation;
import com.hh.contractstestsadmin.service.builder.ValidationBuilder;
import com.hh.contractstestsadmin.service.mapper.ExpectationMapper;
import com.hh.contractstestsadmin.service.mapper.ValidationMapper;
import com.hh.contractstestsadmin.validator.dto.MessageDto;
import com.hh.contractstestsadmin.validator.dto.ValidationDto;
import com.hh.contractstestsadmin.validator.dto.WrongExpectationDto;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.transaction.annotation.Transactional;

public class ValidationService {

  private final ValidationDao validationDao;

  private final ValidationInfoDao validationInfoDao;

  private final ReleaseVersionDao releaseVersionDao;

  private final String minioReleaseName;

  private final ServiceDao serviceDao;

  private final ErrorTypeDao errorTypeDao;

  private final ValidationBuilder validationBuilder;

  public ValidationService(
      ValidationDao validationDao, ReleaseVersionDao releaseVersionDao, ValidationInfoDao validationInfoDao,
      ServiceDao serviceDao,
      ErrorTypeDao errorTypeDao,
      String minioReleaseName,
      ValidationBuilder validationBuilder
  ) {
    this.validationDao = validationDao;
    this.releaseVersionDao = releaseVersionDao;
    this.validationInfoDao = validationInfoDao;
    this.minioReleaseName = minioReleaseName;
    this.serviceDao = serviceDao;
    this.errorTypeDao = errorTypeDao;
    this.validationBuilder = validationBuilder;
  }

  @Transactional
  public List<ValidationMetaInfoDto> getLatestValidationPreviews(String standName, Integer validationPreviewsCount) {
    return validationDao
        .getLatestValidations(standName, validationPreviewsCount)
        .stream()
        .map(ValidationMapper::map)
        .toList();
  }

  @Transactional
  public Validation createValidation(String standName) {
    Validation validation = new Validation();
    validation.setCreationDate(OffsetDateTime.now());
    validation.setStandName(standName);
    validation.setStatus(ValidationStatus.IN_PROGRESS);
    validation.setReleaseInformationVersion(releaseVersionDao.getCurrentReleaseVersion());
    validationDao.save(validation);
    return validation;
  }

  @Transactional
  public ValidationWithRelationsDto getServiceRelation(Long validationId, String standName) {
    Optional<Validation> validationFound = validationDao.getValidation(validationId, standName);
    Validation validation = validationFound.orElseThrow(() -> new ValidationHistoryNotFoundException("not found validation with id " + validationId));
    List<ServiceRelation> serviceRelations = validationInfoDao.getServiceRelations(validationId, ErrorLevel.ERROR);
    return validationBuilder.buildValidationWithRelationsDto(validation, serviceRelations);
  }

  @Transactional
  public List<ExpectationDto> getExpectations(String standName, Long validationId, Long producerId, Long consumerId) {
    Optional<Validation> validationFound = validationDao.getValidation(validationId, standName);
    if (validationFound.isEmpty()) {
      throw new ValidationHistoryNotFoundException("not found validation with stand name: " + standName +
          " and validation with id: " + validationId);
    }

    Optional<Service> producer = serviceDao.getService(producerId);
    if (producer.isEmpty()) {
      throw new ServiceNotFoundException("not found producer with id: " + producerId);
    }

    Optional<Service> consumer = serviceDao.getService(consumerId);
    if (consumer.isEmpty()) {
      throw new ServiceNotFoundException("not found consumer with id: " + consumerId);
    }

    return validationInfoDao.getExpectations(standName, validationId, consumerId, producerId, ErrorLevel.ERROR).stream()
        .map(ExpectationMapper::mapFromEntity)
        .toList();
  }

  @Transactional
  public String getValidationReport(String standName, Long validationId) {
    Optional<Validation> validationFound = validationDao.getValidation(validationId, standName);
    Validation validation = validationFound.orElseThrow(() ->
        new ValidationHistoryNotFoundException("not found validation with stand name: " + standName +
            " and validation with id: " + validationId)
    );
    return validation.getReport();
  }



  @Transactional
  public void recordValidationResult(Long validationId, ValidationDto validationResult) throws ValidationResultRecordException {
    Validation validation = validationDao
        .getValidationById(validationId)
        .orElseThrow(() -> new ValidationResultRecordException(
            "Impossible to record validation result because validation was not found by id='" + validationId + "'")
        );
    validation.setExecutionDate(OffsetDateTime.now());
    validation.setReport(validationResult.getValidatorReport());

    List<WrongExpectationDto> wrongExpectationDtos = Optional
        .ofNullable(validationResult.getWrongExpectations())
        .orElse(Collections.emptyList());
    ExpectationsService expectationsService = new ExpectationsService(validation.getStandName());
    List<Expectation> expectations = expectationsService.convertAndPersistExpectations(wrongExpectationDtos);

    int errorCount = 0;
    for (Expectation expectation : expectations) {
      validation.addExpectation(expectation);
      errorCount += expectation.getContractTestErrors().stream()
              .filter(error -> ErrorLevel.ERROR == error.getLevel())
              .count();
    }
    validation.setErrorCount(errorCount);
    validation.setStatus(ValidationStatus.getValidationStatus(errorCount));
    validationInfoDao.updateValidationInfo(validation);
  }

  private class ExpectationsService {

    private final ErrorTypesContextManager errorTypesContextManager = new ErrorTypesContextManager();
    private final ServicesContextManager servicesContextManager = new ServicesContextManager();

    private final String standName;

    public ExpectationsService(String standName) {
      this.standName = standName;
    }

    public List<Expectation> convertAndPersistExpectations(List<WrongExpectationDto> wrongExpectations) {
      return wrongExpectations.stream()
          .map(this::convertAndPersistExpectation)
          .toList();
    }

    private Expectation convertAndPersistExpectation(WrongExpectationDto wrongExpectation) {
      Expectation expectation = new Expectation();
      expectation.linkWithConsumer(
          servicesContextManager.getOrCreateService(
              wrongExpectation.getConsumerName(),
              wrongExpectation.getConsumerIsRelease() ? minioReleaseName : standName,
              wrongExpectation.getConsumerVersion()
          )
      );
      expectation.linkWithProducer(
          servicesContextManager.getOrCreateService(
              wrongExpectation.getProducerName(),
              wrongExpectation.getProducerIsRelease() ? minioReleaseName : standName,
              wrongExpectation.getProducerVersion()
          )
      );
      expectation.setHttpMethod(wrongExpectation.getRequest().getMethod());
      expectation.setRequestPath(wrongExpectation.getRequest().getPath());
      expectation.setRequestHeaders(wrongExpectation.getRequest().getHeaders());
      expectation.setQueryParams(wrongExpectation.getRequest().getQueryParams());
      expectation.setRequestBody(wrongExpectation.getRequest().getBody());
      expectation.setResponseStatus(wrongExpectation.getResponse().getStatus());
      expectation.setResponseHeaders(wrongExpectation.getResponse().getHeaders());
      expectation.setResponseBody(wrongExpectation.getResponse().getBody());
      for (MessageDto message : wrongExpectation.getMessages()) {
        expectation.addContractTestError(convertAndPersistContractTestError(message));
      }
      return expectation;
    }

    private ContractTestError convertAndPersistContractTestError(MessageDto message) {
      ContractTestError contractTestError = new ContractTestError();
      contractTestError.setErrorType(errorTypesContextManager.getOrCreateErrorType(message.getKey()));
      contractTestError.setErrorMessage(message.getMessage());
      contractTestError.setLevel(message.getLevel());
      return contractTestError;
    }

    private void retryIfBreak(Runnable runnable) {
      for (int i = 2; i >= 0; i--) {
        try {
          runnable.run();
          break;
        } catch (Exception exception) {
          if (i < 1) {
            throw exception;
          }
        }
      }
    }

    private class ErrorTypesContextManager {

      private final Map<String, ErrorType> errorTypesContext = new HashMap<>();

      public ErrorType getOrCreateErrorType(String errorKey) {
        if (!errorTypesContext.containsKey(errorKey)) {
          retryIfBreak(() -> {
            Optional<ErrorType> errorTypeFromDb = errorTypeDao.getErrorTypeByKey(errorKey, LockModeType.PESSIMISTIC_WRITE);
            if (errorTypeFromDb.isPresent()) {
              errorTypesContext.put(errorKey, errorTypeFromDb.get());
            } else {
              ErrorType newErrorType = createErrorTypeFromKey(errorKey);
              errorTypeDao.saveErrorType(newErrorType);
              errorTypesContext.put(errorKey, newErrorType);
            }
          });
        }
        return errorTypesContext.get(errorKey);
      }

      private ErrorType createErrorTypeFromKey(String errorKey) {
        ErrorType errorType = new ErrorType();
        errorType.setErrorKey(errorKey);
        errorType.setVersion(0);
        return errorType;
      }
    }

    private class ServicesContextManager {

      private final Map<ServiceSearchToken, Service> servicesContext = new HashMap<>();

      public Service getOrCreateService(String serviceName, String standName, String version) {
        ServiceSearchToken serviceSearchToken = new ServiceSearchToken(serviceName, standName, version);
        if (!servicesContext.containsKey(serviceSearchToken)) {
          retryIfBreak(() -> {
            Optional<Service> serviceFromDb = serviceDao.findServiceByFields(serviceName, standName, version, LockModeType.PESSIMISTIC_WRITE);
            if (serviceFromDb.isPresent()) {
              servicesContext.put(serviceSearchToken, serviceFromDb.get());
            } else {
              Service newService = createServiceFromToken(serviceSearchToken);
              serviceDao.saveService(newService);
              servicesContext.put(serviceSearchToken, newService);
            }
          });
        }
        return servicesContext.get(serviceSearchToken);
      }

      private Service createServiceFromToken(ServiceSearchToken token) {
        Service service = new Service();
        service.setServiceName(token.serviceName());
        service.setStandName(token.standName());
        service.setTag(token.version());
        service.setServiceType(ServiceType.NOT_DEFINED);
        service.setCreationDate(OffsetDateTime.now());
        return service;
      }

      private record ServiceSearchToken(String serviceName, String standName, String version) {
      }
    }
  }

}
