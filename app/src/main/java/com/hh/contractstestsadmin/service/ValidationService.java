package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ErrorTypeDao;
import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dao.ServiceDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dao.ValidationInfoDao;
import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.dto.validator.ValidationDto;
import com.hh.contractstestsadmin.dto.validator.WrongExpectationDto;
import com.hh.contractstestsadmin.exception.ValidationResultRecordException;
import com.hh.contractstestsadmin.model.ErrorType;
import com.hh.contractstestsadmin.model.Expectation;
import com.hh.contractstestsadmin.model.ServiceType;
import com.hh.contractstestsadmin.model.Validation;
import com.hh.contractstestsadmin.service.mapper.ValidationMapper;
import com.hh.contractstestsadmin.service.mapper.ValidationWithRelationsMapper;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class ValidationService {

  private final ValidationDao validationDao;

  private final ValidationInfoDao validationInfoDao;

  private final ReleaseVersionDao releaseVersionDao;

  private final String minioReleaseName;

  private final ServiceDao serviceDao;

  private final ErrorTypeDao errorTypeDao;

  public ValidationService(
      ValidationDao validationDao, ReleaseVersionDao releaseVersionDao, ValidationInfoDao validationInfoDao,
      ServiceDao serviceDao,
      ErrorTypeDao errorTypeDao,
      String minioReleaseName
  ) {
    this.validationDao = validationDao;
    this.releaseVersionDao = releaseVersionDao;
    this.validationInfoDao = validationInfoDao;
    this.minioReleaseName = minioReleaseName;
    this.serviceDao = serviceDao;
    this.errorTypeDao = errorTypeDao;
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
    List<ServiceRelation> serviceRelations = validationInfoDao.getServiceRelations(validationId);
    return ValidationWithRelationsMapper.map(validation, serviceRelations, minioReleaseName);
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
    int errorCount = 0;
    WrongExpectationsMapper wrongExpectationsMapper = new WrongExpectationsMapper(validation.getStandName());
    for (Expectation expectation : wrongExpectationsMapper.mapToExpectationEntities(validationResult.getWrongExpectations())) {
      validation.addExpectation(expectation);
      errorCount += expectation.getContractTestErrors().size();
    }
    validation.setErrorCount(errorCount);
    validation.setStatus(ValidationStatus.getValidationStatus(errorCount));
    validationInfoDao.updateValidationInfo(validation);
  }

  private class WrongExpectationsMapper {

    private final ErrorTypesContextManager errorTypesContextManager = new ErrorTypesContextManager();
    private final ServicesContextManager servicesContextManager = new ServicesContextManager();

    private final String standName;

    public WrongExpectationsMapper(String standName) {
      this.standName = standName;
    }

    public List<Expectation> mapToExpectationEntities(List<WrongExpectationDto> wrongExpectations) {
      return wrongExpectations.stream()
          .map(this::mapToExpectationEntity)
          .toList();
    }

    private Expectation mapToExpectationEntity(WrongExpectationDto wrongExpectation) {
      return null;
    }

    private class ErrorTypesContextManager {

      private final Map<String, ErrorType> errorTypesContext = new HashMap<>();

      public ErrorType getOrCreateErrorType(String errorKey) {
        if (!errorTypesContext.containsKey(errorKey)) {
          Optional<ErrorType> errorTypeFromDb = errorTypeDao.getErrorTypeByKey(errorKey);
          if (errorTypeFromDb.isPresent()) {
            errorTypesContext.put(errorKey, errorTypeFromDb.get());
          } else {
            errorTypesContext.put(errorKey, new ErrorType(errorKey));
          }
        }
        return errorTypesContext.get(errorKey);
      }

    }

    private class ServicesContextManager {

      private final Map<ServiceSearchToken, ErrorType> errorTypesContext = new HashMap<>();

      public Service getOrCreateService(String serviceName, String standName, String version) {
        return null;
      }

      private Service createServiceFromToken(ServiceSearchToken token){
        Service service = new Service();
        service.setServiceName(token.serviceName());
        service.setStandName(token.standName());
        service.setTag(token.version());
        service.setServiceType(ServiceType.NOT_DEFINED);
        return service;
      }

      private record ServiceSearchToken(String serviceName, String standName, String version) {

      }
    }
  }

}
