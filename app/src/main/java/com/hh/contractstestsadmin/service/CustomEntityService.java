package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ValidationInfoDao;
import com.hh.contractstestsadmin.dto.api.ErrorTypeDto;
import com.hh.contractstestsadmin.model.ErrorType;
import com.hh.contractstestsadmin.service.mapper.ErrorTypeMapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

public class CustomEntityService {
  private final ValidationInfoDao validationInfoDao;

  @Inject
  public CustomEntityService(ValidationInfoDao validationInfoDao) {
    this.validationInfoDao = validationInfoDao;
  }

  @Transactional
  public void createErrorType(List<ErrorTypeDto> errorTypeDtos) {
    Objects.requireNonNull(errorTypeDtos);
    for (ErrorTypeDto errorTypeDto : errorTypeDtos) {
      Optional<ErrorType> findErrorType = validationInfoDao.getErrorTypeByKey(errorTypeDto.getKey());
      if (findErrorType.isPresent()) {
        throw new IllegalArgumentException("error key with id " + errorTypeDto.getKey() + " must be unique");
      }

      if (errorTypeDto.getKey() == null || errorTypeDto.getComment() == null) {
        throw new IllegalArgumentException("entity field can not null");
      }

      ErrorType errorType = ErrorTypeMapper.mapToEntity(errorTypeDto);
      errorType.setErrorKey(modifiedLengthIfNeed(errorType.getErrorKey(), 2048));
      errorType.setComments(modifiedLengthIfNeed(errorType.getComments(), 4096));
      validationInfoDao.saveErrorType(errorType);
    }
  }

  public String modifiedLengthIfNeed(String field, int maxLength) {
    if (field != null && field.length() > maxLength) {
      return field.substring(0, 4096);
    }
    return field;
  }

  @Transactional
  public void updateErrorType(List<ErrorTypeDto> errorTypeDtos) {
    Objects.requireNonNull(errorTypeDtos);
    for (ErrorTypeDto errorTypeDto : errorTypeDtos) {
      if (errorTypeDto.getKey() == null) {
        throw new IllegalArgumentException("entity field can not null");
      }
      ErrorType errorType = ErrorTypeMapper.mapToEntity(errorTypeDto);
      errorType.setComments(modifiedLengthIfNeed(errorType.getComments(), 4096));
      validationInfoDao.updateErrorType(errorType);
    }
  }

  @Transactional
  public void deleteErrorType(String errorKey) {
    validationInfoDao.deleteErrorTypeByKey(errorKey);
  }

  @Transactional
  public Optional<ErrorTypeDto> getErrorTypeByKey(String errorKey) {
    return validationInfoDao.getErrorTypeByKey(errorKey)
        .map(ErrorTypeMapper::mapFromEntity);
  }

  @Transactional
  public List<ErrorTypeDto> getAllErrorType() {
    return Optional.ofNullable(validationInfoDao.getAllErrorTypes())
        .orElseGet(Collections::emptyList)
        .stream()
        .map(ErrorTypeMapper::mapFromEntity)
        .toList();
  }


}
