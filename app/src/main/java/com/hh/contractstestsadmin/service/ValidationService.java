package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ErrorTypeDao;
import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dao.ServiceDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dao.ValidationInfoDao;
import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.hh.contractstestsadmin.dto.validator.MessageDto;
import com.hh.contractstestsadmin.dto.validator.ValidationDto;
import com.hh.contractstestsadmin.dto.validator.WrongExpectationDto;
import com.hh.contractstestsadmin.exception.ValidationResultRecordException;
import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ErrorType;
import com.hh.contractstestsadmin.model.Expectation;
import com.hh.contractstestsadmin.model.Validation;
import com.hh.contractstestsadmin.model.artefacts.Service;
import com.hh.contractstestsadmin.service.util.ErrorTypesContextManager;
import com.hh.contractstestsadmin.service.util.ServicesContextManager;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

public class ValidationService {

  private final ValidationDao validationDao;

  private final ReleaseVersionDao releaseVersionDao;

  private final ValidationInfoDao validationInfoDao;

  private final ServiceDao serviceDao;

  private final ErrorTypeDao errorTypeDao;

  public ValidationService(
      ValidationDao validationDao, ReleaseVersionDao releaseVersionDao, ValidationInfoDao validationInfoDao,
      ServiceDao serviceDao,
      ErrorTypeDao errorTypeDao
  ) {
    this.validationDao = validationDao;
    this.releaseVersionDao = releaseVersionDao;
    this.validationInfoDao = validationInfoDao;
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
    validation.setCreatedDate(OffsetDateTime.now());
    validation.setStandName(standName);
    validation.setStatus(ValidationStatus.IN_PROGRESS);
    validation.setReleaseInformationVersion(releaseVersionDao.getCurrentReleaseVersion());
    validationDao.save(validation);
    return validation;
  }

  @Transactional
  public void recordValidationResult(Long validationId, ValidationDto validationResult) throws ValidationResultRecordException {
    Validation validation = validationDao
        .getValidationById(validationId)
        .orElseThrow(() -> new ValidationResultRecordException(
            "Impossible to record validation result because validation was not found by id='" + validationId + "'")
        );
    validation.setExecuteDate(OffsetDateTime.now());
    validation.setValidatorErrors(validationResult.getValidatorReport());
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

    public WrongExpectationsMapper(String standName){
      this.standName = standName;
    }

    public List<Expectation> mapToExpectationEntities(List<WrongExpectationDto> wrongExpectations) {
      return new ArrayList<>();
    }

    private Expectation mapToExpectationEntity(WrongExpectationDto wrongExpectation) {
      return null;
    }

    private class ErrorTypesContextManager {

      public ErrorType getOrCreateErrorType(String errorKey) {
        return null;
      }

    }

    private class ServicesContextManager {

      public Service getOrCreateService(String serviceName, String standName, String version) {
        return null;
      }

    }
  }

}
