package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dto.api.ValidationPreviewDto;
import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.hh.contractstestsadmin.dto.validator.ValidationDto;
import com.hh.contractstestsadmin.exception.ValidationRecordException;
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
  public List<ValidationPreviewDto> getLatestValidationPreviews(String standName, Integer validationPreviewsCount) {
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
  public void recordValidation(Long validationId, ValidationDto validationDto) throws ValidationRecordException {
    Validation validation = validationDao
        .getValidationById(validationId)
        .orElseThrow(() -> new ValidationRecordException(""));

  }
}
