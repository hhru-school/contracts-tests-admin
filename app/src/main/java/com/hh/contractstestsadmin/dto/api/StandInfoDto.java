package com.hh.contractstestsadmin.dto.api;

public class StandInfoDto {

  private String name;

  private Boolean isRelease;

  public StandInfoDto(String name, Boolean isRelease) {
    this.name = name;
    this.isRelease = isRelease;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getIsRelease() {
    return isRelease;
  }

  public void setIsRelease(Boolean isRelease) {
    this.isRelease = isRelease;
  }
}
