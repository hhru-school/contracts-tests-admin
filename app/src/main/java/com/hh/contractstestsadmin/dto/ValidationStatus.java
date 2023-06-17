package com.hh.contractstestsadmin.dto;

public enum ValidationStatus {
  IN_PROGRESS,
  SUCCESS,
  FAILED,
  CANCELLED;

  public static ValidationStatus getValidationStatus(int errorCount) {
    if (errorCount > 0) {
      return ValidationStatus.FAILED;
    }
    if (errorCount == 0) {
      return ValidationStatus.SUCCESS;
    }
    return ValidationStatus.CANCELLED;
  }
}
