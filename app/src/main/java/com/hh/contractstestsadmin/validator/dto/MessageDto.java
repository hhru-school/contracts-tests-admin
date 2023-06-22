package com.hh.contractstestsadmin.validator.dto;

import com.hh.contractstestsadmin.model.ErrorLevel;

public class MessageDto {

  private String key;

  private String message;

  private ErrorLevel level;

  private Integer responseStatus;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorLevel getLevel() {
    return level;
  }

  public void setLevel(ErrorLevel level) {
    this.level = level;
  }

  public Integer getResponseStatus() {
    return responseStatus;
  }

  public void setResponseStatus(Integer responseStatus) {
    this.responseStatus = responseStatus;
  }
}
