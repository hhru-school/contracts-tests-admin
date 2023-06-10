package com.hh.contractstestsadmin.dto.api;

public class ProducerDto extends ServiceDto {

  private String schemaLink;

  public ProducerDto() {
  }

  public String getSchemaLink() {
    return schemaLink;
  }

  public void setSchemaLink(String schemaLink) {
    this.schemaLink = schemaLink;
  }

}
