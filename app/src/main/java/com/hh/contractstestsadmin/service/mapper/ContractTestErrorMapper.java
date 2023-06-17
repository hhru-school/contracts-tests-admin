package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ErrorDto;
import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ErrorType;
import com.hh.contractstestsadmin.validator.dto.MessageDto;

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

  public static ContractTestError mapToEntity(MessageDto message) {
    ContractTestError contractTestError = new ContractTestError();
    contractTestError.setErrorType(new ErrorType(message.getKey()));
    contractTestError.setErrorMessage(message.getMessage());
    contractTestError.setLevel(message.getLevel());
    return contractTestError;
  }

}
