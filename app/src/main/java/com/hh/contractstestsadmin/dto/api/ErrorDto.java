package com.hh.contractstestsadmin.dto.api;

import com.hh.contractstestsadmin.model.ErrorLevel;

public class ErrorDto {

  private Long id;

  private ErrorTypeDto errorType;

  private ErrorLevel errorLevel;

  private String message;

  public ErrorDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ErrorTypeDto getErrorType() {
    return errorType;
  }

  public void setErrorType(ErrorTypeDto errorType) {
    this.errorType = errorType;
  }

  public ErrorLevel getErrorLevel() {
    return errorLevel;
  }

  public void setErrorLevel(ErrorLevel errorLevel) {
    this.errorLevel = errorLevel;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
