package com.hh.contractstestsadmin.dto.api;

public class ServiceDto {

  private Long id;

  private String name;

  private boolean release;

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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isRelease() {
    return release;
  }

  public void setRelease(boolean release) {
    this.release = release;
  }
}
