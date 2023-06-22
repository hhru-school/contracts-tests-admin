package com.hh.contractstestsadmin.dto;

public enum ValidationStatus {
  IN_PROGRESS,
  SUCCESS,
  FAILED;

  public static ValidationStatus getValidationStatus(long errorCount) {
    return errorCount > 0
            ? ValidationStatus.FAILED
            : ValidationStatus.SUCCESS;
  }
}
