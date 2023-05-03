package com.hh.contractstestsadmin.dto;

public class StandStatusDto {

  private String standName;

  private Boolean isRelease;

  private String releaseLink;

  private ServicesContainerDto services;

  public Boolean getIsRelease() {
    return isRelease;
  }

  public void setIsRelease(Boolean isRelease) {
    this.isRelease = isRelease;
  }

  public String getReleaseLink() {
    return releaseLink;
  }

  public void setReleaseLink(String releaseLink) {
    this.releaseLink = releaseLink;
  }

  public Boolean getRelease() {
    return isRelease;
  }

  public void setRelease(Boolean release) {
    isRelease = release;
  }

  public ServicesContainerDto getServices() {
    return services;
  }

  public void setServices(ServicesContainerDto services) {
    this.services = services;
  }

  public String getStandName() {
    return standName;
  }

  public void setStandName(String standName) {
    this.standName = standName;
  }
}
