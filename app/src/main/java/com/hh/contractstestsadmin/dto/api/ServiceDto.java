package com.hh.contractstestsadmin.dto.api;

import com.hh.contractstestsadmin.model.ServiceType;

public class ServiceDto {

  private Long id;

  private String name;

  private boolean release;

  private String version;

  private ServiceType serviceType;

  private String fileLink;

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

  public ServiceType getServiceType() {
    return serviceType;
  }

  public void setServiceType(ServiceType serviceType) {
    this.serviceType = serviceType;
  }

  public String getFileLink() {
    return fileLink;
  }

  public void setFileLink(String fileLink) {
    this.fileLink = fileLink;
  }
}
