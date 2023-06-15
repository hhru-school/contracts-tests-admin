package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ErrorTypeDao;
import com.hh.contractstestsadmin.dto.api.ErrorTypeDto;
import com.hh.contractstestsadmin.exception.IllegalErrorTypeArgumentException;
import com.hh.contractstestsadmin.model.ErrorType;
import com.hh.contractstestsadmin.service.mapper.ErrorTypeMapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CustomEntityService {
  private final ErrorTypeDao errorTypeDao;
  private static final int MAX_SIZE_COMMENT = 4096;

  @Inject
  public CustomEntityService(ErrorTypeDao errorTypeDao) {

    this.errorTypeDao = errorTypeDao;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createErrorTypes(List<ErrorTypeDto> errorTypeDtos) {
    Objects.requireNonNull(errorTypeDtos);
    errorTypeDtos.forEach(this::createErrorType);
  }

  @Transactional
  public void createErrorType(ErrorTypeDto errorTypeDto) {
    Optional<ErrorType> findErrorType = errorTypeDao.getErrorTypeByKey(errorTypeDto.getKey());
    if (findErrorType.isPresent()) {
      throw new IllegalErrorTypeArgumentException("error key with id " + errorTypeDto.getKey() + " must be unique");
    }

    if (errorTypeDto.getKey() == null || errorTypeDto.getComment() == null) {
      throw new IllegalErrorTypeArgumentException("entity field can not null");
    }
    validateLengthString(errorTypeDto.getKey(), 2048);
    validateLengthString(errorTypeDto.getComment(), MAX_SIZE_COMMENT);

    ErrorType errorType = ErrorTypeMapper.mapToEntity(errorTypeDto);
    errorType.setVersion(0);
    errorTypeDao.saveErrorType(errorType);
  }

  public void validateLengthString(String field, int maxLength) {
    if (field != null && field.length() > maxLength) {
      throw new IllegalErrorTypeArgumentException("maximum allowed string length " + maxLength);
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateErrorTypes(List<ErrorTypeDto> errorTypeDtos) {
    Objects.requireNonNull(errorTypeDtos);
    errorTypeDtos.forEach(this::updateErrorType);
  }

  @Transactional
  public void updateErrorType(ErrorTypeDto errorTypeDto) {
    if (errorTypeDto.getKey() == null) {
      throw new IllegalArgumentException("entity field can not null");
    }
    validateLengthString(errorTypeDto.getComment(), MAX_SIZE_COMMENT);
    ErrorType errorType = ErrorTypeMapper.mapToEntity(errorTypeDto);
    errorType.setComment(errorTypeDto.getComment());
    errorTypeDao.updateErrorType(errorType);
  }

  @Transactional
  public void deleteErrorType(String errorKey) {
    errorTypeDao.deleteErrorTypeByKey(errorKey);
  }

  @Transactional
  public Optional<ErrorTypeDto> getErrorTypeByKey(String errorKey) {
    return errorTypeDao.getErrorTypeByKey(errorKey)
        .map(ErrorTypeMapper::mapFromEntity);
  }

  @Transactional
  public List<ErrorTypeDto> getAllErrorType() {
    return Optional.ofNullable(errorTypeDao.getAllErrorTypes())
        .orElseGet(Collections::emptyList)
        .stream()
        .map(ErrorTypeMapper::mapFromEntity)
        .toList();
  }

}
