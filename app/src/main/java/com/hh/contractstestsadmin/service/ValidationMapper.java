package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.model.Validation;

public class ValidationMapper extends Mapper {

  public static ValidationPreviewDto map(Validation validation) {
    ValidationPreviewDto validationPreviewDto = new ValidationPreviewDto();
    validationPreviewDto.setId(validation.getId());
    validationPreviewDto.setCreatedDate(convertDateTime(validation.getCreatedDate()));
    if (validation.getExecuteDate() != null) {
      validationPreviewDto.setExecuteDate(convertDateTime(validation.getExecuteDate()));
    }
    validationPreviewDto.setStatus(validation.getStatus());
    validationPreviewDto.setReleaseLink(validation.getReleaseInformationVersion());
    validationPreviewDto.setErrorCount(validation.getErrorCount());
    return validationPreviewDto;
  }
}
