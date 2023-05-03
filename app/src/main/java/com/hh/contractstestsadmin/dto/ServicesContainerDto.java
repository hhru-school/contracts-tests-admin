package com.hh.contractstestsadmin.dto;

import java.util.List;

public class ServicesContainerDto {

  private List<ServiceDto> stand;

  private List<ServiceDto> release;

  public List<ServiceDto> getStand() {
    return stand;
  }

  public void setStand(List<ServiceDto> stand) {
    this.stand = stand;
  }

  public List<ServiceDto> getRelease() {
    return release;
  }

  public void setRelease(List<ServiceDto> release) {
    this.release = release;
  }
}
