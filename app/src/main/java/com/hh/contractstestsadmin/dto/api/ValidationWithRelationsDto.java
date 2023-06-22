package com.hh.contractstestsadmin.dto.api;

import java.util.ArrayList;
import java.util.List;

public class ValidationWithRelationsDto extends ValidationMetaInfoDto {

  private List<ServicesRelationDto> servicesRelations = new ArrayList<>();

  public ValidationWithRelationsDto() {
  }

  public ValidationWithRelationsDto(ValidationMetaInfoDto validationMetaInfoDto) {
    this.setId(validationMetaInfoDto.getId());
    this.setCreatedDate(validationMetaInfoDto.getCreatedDate());
    this.setExecuteDate(validationMetaInfoDto.getExecuteDate());
    this.setReleaseLink(validationMetaInfoDto.getReleaseLink());
    this.setStatus(validationMetaInfoDto.getStatus());
    this.setErrorCount(validationMetaInfoDto.getErrorCount());
  }

  public List<ServicesRelationDto> getServicesRelations() {
    return servicesRelations;
  }

  public void setServicesRelations(List<ServicesRelationDto> servicesRelations) {
    this.servicesRelations = servicesRelations;
  }
}
