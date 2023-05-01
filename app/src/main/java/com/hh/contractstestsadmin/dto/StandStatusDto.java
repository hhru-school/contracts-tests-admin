package com.hh.contractstestsadmin.dto;

import java.util.List;

public class StandStatusDto {

  private Boolean isRelease;

  private String releaseLink;

  private List<ServiceDto> standServices;

  private List<ServiceDto> releaseServices;

  private List<ValidationPreviewDto> historyPreview;

  public Boolean getIsRelease() {
    return isRelease;
  }

  public void setIsRelease(Boolean isRelease) {
    this.isRelease = isRelease;
  }

  public List<ServiceDto> getStandServices() {
    return standServices;
  }

  public void setStandServices(List<ServiceDto> standServices) {
    this.standServices = standServices;
  }

  public String getReleaseLink() {
    return releaseLink;
  }

  public void setReleaseLink(String releaseLink) {
    this.releaseLink = releaseLink;
  }

  public List<ServiceDto> getReleaseServices() {
    return releaseServices;
  }

  public void setReleaseServices(List<ServiceDto> releaseServices) {
    this.releaseServices = releaseServices;
  }

  public List<ValidationPreviewDto> getHistoryPreview() {
    return historyPreview;
  }

  public void setHistoryPreview(List<ValidationPreviewDto> historyPreview) {
    this.historyPreview = historyPreview;
  }
}
