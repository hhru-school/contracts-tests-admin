package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.model.Validation;

public class ValidationMapper {

  public static ValidationMetaInfoDto map(Validation validation) {
    ValidationMetaInfoDto validationMetaInfoDto = new ValidationMetaInfoDto();
    validationMetaInfoDto.setId(validation.getId());
    validationMetaInfoDto.setCreatedDate(validation.getCreatedDate());
    validationMetaInfoDto.setExecuteDate(validation.getExecuteDate());
    validationMetaInfoDto.setStatus(validation.getStatus());
    validationMetaInfoDto.setReleaseLink(validation.getReleaseInformationVersion());
    validationMetaInfoDto.setErrorCount(validation.getErrorCount());
    return validationMetaInfoDto;
  }
}
