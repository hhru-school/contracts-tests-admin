package com.hh.contractstestsadmin.dto;

import java.util.List;

public class ServicesContainerDto {

  private List<ServiceDto> standServices;

  private List<ServiceDto> releaseServices;

  public List<ServiceDto> getStandServices() {
    return standServices;
  }

  public void setStandServices(List<ServiceDto> standServices) {
    this.standServices = standServices;
  }

  public List<ServiceDto> getReleaseServices() {
    return releaseServices;
  }

  public void setReleaseServices(List<ServiceDto> releaseServices) {
    this.releaseServices = releaseServices;
  }
}
