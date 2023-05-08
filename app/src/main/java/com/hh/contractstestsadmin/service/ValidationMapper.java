package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.model.Validation;

import java.time.ZoneOffset;

public class ValidationMapper {

  public static ValidationPreviewDto map(Validation validation) {
    ValidationPreviewDto validationPreviewDto = new ValidationPreviewDto();
    validationPreviewDto.setId(validation.getId());
    validationPreviewDto.setCreatedDate(validation.getCreatedDate()
        .atOffset(ZoneOffset.UTC));
    if (validation.getExecuteDate() != null) {
      validationPreviewDto.setExecuteDate(validation.getExecuteDate()
          .atOffset(ZoneOffset.UTC));
    }
    validationPreviewDto.setStatus(validation.getStatus());
    validationPreviewDto.setReleaseLink(validation.getReleaseInformationVersion());
    validationPreviewDto.setErrorCount(validation.getErrorCount());
    return validationPreviewDto;
  }
}
