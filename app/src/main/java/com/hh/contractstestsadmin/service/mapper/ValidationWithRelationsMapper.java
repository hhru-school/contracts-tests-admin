package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ServicesRelationDto;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.Validation;
import java.util.List;

public class ValidationWithRelationsMapper {
  public static ValidationWithRelationsDto map(Validation validation, List<ServiceRelation> serviceRelations, String minioReleaseName) {
    ValidationWithRelationsDto validationWithRelationsDto = new ValidationWithRelationsDto(ValidationMapper.map(validation));
    if (serviceRelations == null) {
      return validationWithRelationsDto;
    }
    List<ServicesRelationDto> servicesRelationDtos = serviceRelations.stream()
        .map(s -> ServiceRelationMapper.map(s, minioReleaseName))
        .toList();
    validationWithRelationsDto.setServicesRelations(servicesRelationDtos);
    return validationWithRelationsDto;
  }
}
