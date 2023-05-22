package com.hh.contractstestsadmin.dto;

import java.util.List;

public class ValidationWithRelationsDto extends ValidationDto {

  private List<ServicesRelationDto> servicesRelations;

  public List<ServicesRelationDto> getServicesRelations() {
    return servicesRelations;
  }

  public void setServicesRelations(List<ServicesRelationDto> servicesRelations) {
    this.servicesRelations = servicesRelations;
  }
}
