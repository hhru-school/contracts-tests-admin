package com.hh.contractstestsadmin.dto.validator;

import com.hh.contractstestsadmin.model.ErrorLevel;

public class MessageDto {

  private String key;

  private String message;

  private ErrorLevel level;

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
}
