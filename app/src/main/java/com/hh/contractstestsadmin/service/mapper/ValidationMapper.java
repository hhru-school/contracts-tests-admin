package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.model.Validation;
import java.util.Objects;

public class ValidationMapper {

  public static ValidationMetaInfoDto map(Validation validation) {
    Objects.requireNonNull(validation);
    ValidationMetaInfoDto validationMetaInfoDto = new ValidationMetaInfoDto();
    validationMetaInfoDto.setId(validation.getId());
    validationMetaInfoDto.setCreatedDate(validation.getCreationDate());
    validationMetaInfoDto.setExecuteDate(validation.getExecutionDate());
    validationMetaInfoDto.setStatus(validation.getStatus());
    validationMetaInfoDto.setReleaseLink(validation.getReleaseInformationVersion());
    validationMetaInfoDto.setErrorCount(validation.getErrorCount());
    return validationMetaInfoDto;
  }
}
