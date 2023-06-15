package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ErrorTypeDto;
import com.hh.contractstestsadmin.model.ErrorType;
import java.util.Objects;

public class ErrorTypeMapper {

  public static ErrorTypeDto mapFromEntity(ErrorType errorType) {
    Objects.requireNonNull(errorType);
    return new ErrorTypeDto(errorType.getErrorKey(), errorType.getComment(), errorType.getVersion());
  }

  public static ErrorType mapToEntity(ErrorTypeDto errorTypeDto) {
    int version = errorTypeDto.getVersion() == null ? 0 : errorTypeDto.getVersion();
    return new ErrorType(errorTypeDto.getKey(), errorTypeDto.getComment(), version);
  }

}
