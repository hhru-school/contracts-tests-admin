package com.hh.contractstestsadmin.model;

import java.util.List;

public class Stand {

  private String name;

  public List<Service> getServices() {
    return services;
  }

  public void setServices(List<Service> services) {
    this.services = services;
  }

  private List<Service> services;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}


