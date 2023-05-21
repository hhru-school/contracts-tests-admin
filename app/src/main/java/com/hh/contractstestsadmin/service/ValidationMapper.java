package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ValidationDto;
import com.hh.contractstestsadmin.model.Validation;

public class ValidationMapper {

  public static ValidationDto map(Validation validation) {
    ValidationDto validationDto = new ValidationDto();
    validationDto.setId(validation.getId());
    validationDto.setCreatedDate(validation.getCreatedDate());
    validationDto.setExecuteDate(validation.getExecuteDate());
    validationDto.setStatus(validation.getStatus());
    validationDto.setReleaseLink(validation.getReleaseInformationVersion());
    validationDto.setErrorCount(validation.getErrorCount());
    return validationDto;
  }
}
