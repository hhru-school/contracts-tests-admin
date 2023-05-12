package com.hh.contractstestsadmin.dto;

import java.util.List;

public class ValidationDto extends ValidationPreviewDto {

  private List<RelationDto> relations;

  public List<RelationDto> getRelations() {
    return relations;
  }

  public void setRelations(List<RelationDto> relations) {
    this.relations = relations;
  }
}
