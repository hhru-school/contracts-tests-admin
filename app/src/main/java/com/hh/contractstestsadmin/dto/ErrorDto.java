package com.hh.contractstestsadmin.dto;

import com.hh.contractstestsadmin.model.ErrorLevel;

public class ErrorDto {

  private Long id;

  private ErrorTypeDto errorType;

  private ErrorLevel errorLevel;

  private String comment;

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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
