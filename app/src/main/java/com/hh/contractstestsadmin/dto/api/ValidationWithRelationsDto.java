package com.hh.contractstestsadmin.dto.api;

import java.util.List;

public class ValidationWithRelationsDto extends ValidationMetaInfoDto {

  private List<ServicesRelationDto> servicesRelations;

  public List<ServicesRelationDto> getServicesRelations() {
    return servicesRelations;
  }

  public void setServicesRelations(List<ServicesRelationDto> servicesRelations) {
    this.servicesRelations = servicesRelations;
  }
}
