package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.hh.contractstestsadmin.dto.validator.ValidationDto;
import com.hh.contractstestsadmin.exception.ValidationResultRecordException;
import com.hh.contractstestsadmin.model.Validation;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class ValidationService {

  private final ValidationDao validationDao;

  private final ReleaseVersionDao releaseVersionDao;

  public ValidationService(ValidationDao validationDao, ReleaseVersionDao releaseVersionDao) {
    this.validationDao = validationDao;
    this.releaseVersionDao = releaseVersionDao;
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
  }
}
