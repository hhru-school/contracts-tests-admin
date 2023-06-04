package com.hh.contractstestsadmin.dto.api;

public class ErrorTypeDto {

  private String key;

  private String comment;


  public ErrorTypeDto() {
  }

  public ErrorTypeDto(String key, String comment) {
    this.key = key;
    this.comment = comment;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
