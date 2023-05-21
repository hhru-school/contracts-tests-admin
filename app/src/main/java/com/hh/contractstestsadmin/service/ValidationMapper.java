package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.model.Validation;

public class ValidationMapper {

  public static ValidationPreviewDto map(Validation validation) {
    ValidationPreviewDto validationPreviewDto = new ValidationPreviewDto();
    validationPreviewDto.setId(validation.getId());
    validationPreviewDto.setCreatedDate(validation.getCreatedDate());
    validationPreviewDto.setExecuteDate(validation.getExecuteDate());
    validationPreviewDto.setStatus(validation.getStatus());
    validationPreviewDto.setReleaseLink(validation.getReleaseInformationVersion());
    validationPreviewDto.setErrorCount(validation.getErrorCount());
    return validationPreviewDto;
  }
}
