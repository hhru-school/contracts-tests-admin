package com.hh.contractstestsadmin.service;

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
import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.Validation;
import com.hh.contractstestsadmin.service.mapper.ExpectationMapper;
import com.hh.contractstestsadmin.service.mapper.ValidationMapper;
import com.hh.contractstestsadmin.service.mapper.ValidationWithRelationsMapper;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class ValidationService {

  private final ValidationDao validationDao;
  private final ValidationInfoDao validationInfoDao;
  private final ServiceDao serviceDao;

  private final ReleaseVersionDao releaseVersionDao;
  private final String minioReleaseName;

  public ValidationService(ValidationDao validationDao, ReleaseVersionDao releaseVersionDao, ValidationInfoDao validationInfoDao,
      String minioReleaseName, ServiceDao serviceDao) {
    this.validationDao = validationDao;
    this.releaseVersionDao = releaseVersionDao;
    this.validationInfoDao = validationInfoDao;
    this.minioReleaseName = minioReleaseName;
    this.serviceDao = serviceDao;
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

    return validationInfoDao.getExpectations(standName, validationId, consumerId, producerId).stream()
        .map(ExpectationMapper::mapFromEntity)
        .toList();
  }
}
