package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ErrorDto;
import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ErrorType;

public class ContractTestErrorMapper {
  public static ErrorDto mapFromEntity(ContractTestError error) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.setId(error.getId());
    errorDto.setErrorLevel(error.getLevel());
    errorDto.setMessage(error.getErrorMessage());
    ErrorType errorType = error.getErrorType();
    if (errorType != null) {
      errorDto.setErrorType(ErrorTypeMapper.mapFromEntity(errorType));
    }
    return errorDto;
  }
}
