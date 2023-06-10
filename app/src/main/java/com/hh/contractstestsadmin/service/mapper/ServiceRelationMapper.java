package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ServicesRelationDto;
import com.hh.contractstestsadmin.model.ServiceRelation;
import java.util.Objects;

public class ServiceRelationMapper {
  public static ServicesRelationDto map(ServiceRelation serviceRelation, String minioReleaseName) {
    Objects.requireNonNull(serviceRelation);

    ServicesRelationDto servicesRelationDto = new ServicesRelationDto();
    if (serviceRelation.getProducer() != null) {
      servicesRelationDto.setProducer(ProducerMapper.map(serviceRelation.getProducer(), minioReleaseName));
    }

    if (serviceRelation.getConsumer() != null) {
      servicesRelationDto.setConsumer(ConsumerMapper.map(serviceRelation.getConsumer(), minioReleaseName));
    }
    servicesRelationDto.setErrorCount(serviceRelation.getErrorCount());
    servicesRelationDto.setFailedRequestPathCount(serviceRelation.getFailedRequestPathCount());
    return servicesRelationDto;
  }
}
