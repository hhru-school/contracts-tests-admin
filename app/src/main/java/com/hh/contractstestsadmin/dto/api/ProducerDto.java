package com.hh.contractstestsadmin.dto.api;

import java.util.Objects;

public class ProducerDto extends ServiceDto {

  private String schemaLink;

  public ProducerDto(ServiceDto serviceDto) {
    Objects.requireNonNull(serviceDto);
    this.setId(serviceDto.getId());
    this.setName(serviceDto.getName());
    this.setRelease(serviceDto.isRelease());
    this.setVersion(serviceDto.getVersion());
  }

  public String getSchemaLink() {
    return schemaLink;
  }

  public void setSchemaLink(String schemaLink) {
    this.schemaLink = schemaLink;
  }

}
