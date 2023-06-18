package com.hh.contractstestsadmin.dto;

public enum ValidationStatus {
  IN_PROGRESS,
  SUCCESS,
  FAILED;

  public static ValidationStatus getValidationStatus(int errorCount) {
    if (errorCount > 0) {
      return ValidationStatus.FAILED;
    }
    return ValidationStatus.SUCCESS;
  }
}
