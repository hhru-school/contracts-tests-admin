package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.hh.contractstestsadmin.model.Validation;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.transaction.annotation.Transactional;

public class ValidationService {

  private final ValidationDao validationDao;

  private final ReleaseVersionDao releaseVersionDao;

  public ValidationService(ValidationDao validationDao, ReleaseVersionDao releaseVersionDao) {
    this.validationDao = validationDao;
    this.releaseVersionDao = releaseVersionDao;
  }

  @Transactional
  public List<ValidationPreviewDto> getLatestValidationPreviews(String standName, Long validationPreviewsCount) {
    Stream<ValidationPreviewDto> validationPreviewStream = validationDao
        .getAllValidationsByStandName(standName)
        .stream()
        .map(ValidationMapper::map)
        .sorted(Comparator.comparing(ValidationPreviewDto::getDate).reversed());
    if (validationPreviewsCount == null) {
      return validationPreviewStream.toList();
    }
    return validationPreviewStream
        .limit(validationPreviewsCount)
        .toList();
  }

  @Transactional
  public Validation createValidation(String standName) {
    Validation validation = new Validation();
    validation.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
    validation.setStandName(standName);
    validation.setStatus(ValidationStatus.IN_PROGRESS);
    validation.setReleaseInformationVersion(releaseVersionDao.getCurrentReleaseVersion());
    validationDao.save(validation);
    return validation;
  }
}
