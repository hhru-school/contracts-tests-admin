package com.hh.contractstestsadmin.dto.api;

public class ServiceDto {

  private Long id;

  private String name;

  private Boolean isRelease;

  private String version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
