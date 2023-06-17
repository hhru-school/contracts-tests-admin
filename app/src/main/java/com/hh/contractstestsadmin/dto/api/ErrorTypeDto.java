package com.hh.contractstestsadmin.dto.api;

public class ErrorTypeDto {

  private String key;

  private String comment;

  private Integer version;


  public ErrorTypeDto() {
  }

  public ErrorTypeDto(String key, String comment, int version) {
    this.key = key;
    this.comment = comment;
    this.version = version;
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

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}
