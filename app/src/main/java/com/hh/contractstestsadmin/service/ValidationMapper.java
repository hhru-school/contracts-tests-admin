package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.model.Validation;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ValidationMapper {

  public static ValidationPreviewDto map(Validation validation) {
    ValidationPreviewDto validationPreviewDto = new ValidationPreviewDto();
    validationPreviewDto.setId(validation.getId());
    validationPreviewDto.setCreatedDate(validation.getCreatedDate()
        .atOffset(ZoneOffset.UTC)
        .format(DateTimeFormatter.ISO_INSTANT));
    if (validation.getExecuteDate() != null) {
      validationPreviewDto.setExecuteDate(validation.getExecuteDate()
          .atOffset(ZoneOffset.UTC)
          .format(DateTimeFormatter.ISO_INSTANT));
    }
    validationPreviewDto.setStatus(validation.getStatus());
    validationPreviewDto.setReleaseLink(validation.getReleaseInformationVersion());
    validationPreviewDto.setErrorCount(validation.getErrorCount());
    return validationPreviewDto;
  }
}
