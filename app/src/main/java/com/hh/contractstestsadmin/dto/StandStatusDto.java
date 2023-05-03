package com.hh.contractstestsadmin.dto;

public class StandStatusDto {

  private String standName;

  private Boolean isRelease;

  private String releaseLink;

  private ServicesContainerDto services;

  public String getStandName() {
    return standName;
  }

  public void setStandName(String standName) {
    this.standName = standName;
  }

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

  public ServicesContainerDto getServices() {
    return services;
  }

  public void setServices(ServicesContainerDto services) {
    this.services = services;
  }

}
