package com.hh.contractstestsadmin.dto.api;

import java.util.List;

public class ServicesContainerDto {

  private List<ServiceStatusDto> stand;

  private List<ServiceStatusDto> release;

  public List<ServiceStatusDto> getStand() {
    return stand;
  }

  public void setStand(List<ServiceStatusDto> stand) {
    this.stand = stand;
  }

  public List<ServiceStatusDto> getRelease() {
    return release;
  }

  public void setRelease(List<ServiceStatusDto> release) {
    this.release = release;
  }
}
