package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.api.ServicesRelationDto;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.Validation;
import java.util.List;

public class ValidationWithRelationsMapper {
  public static ValidationWithRelationsDto map(Validation validation, List<ServiceRelation> serviceRelations) {
    ValidationWithRelationsDto validationMetaInfoDto = new ValidationWithRelationsDto(ValidationMapper.map(validation));
    if (serviceRelations == null) {
      return validationMetaInfoDto;
    }
    List<ServicesRelationDto> servicesRelationDtos = serviceRelations.stream()
        .map(ServiceRelationMapper::map)
        .toList();
    validationMetaInfoDto.setServicesRelations(servicesRelationDtos);
    return validationMetaInfoDto;
  }
}
