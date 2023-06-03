package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.api.ConsumerMapper;
import com.hh.contractstestsadmin.dto.api.ProducerMapper;
import com.hh.contractstestsadmin.dto.api.ServicesRelationDto;
import com.hh.contractstestsadmin.model.ServiceRelation;
import java.util.Objects;

public class ServiceRelationMapper extends ServiceMapper {
  public static ServicesRelationDto map(ServiceRelation serviceRelation) {
    Objects.requireNonNull(serviceRelation);

    ServicesRelationDto servicesRelationDto = new ServicesRelationDto();
    if (serviceRelation.getProducer() != null) {
      servicesRelationDto.setProducer(ProducerMapper.map(serviceRelation.getProducer()));
    }

    if (serviceRelation.getConsumer() != null) {
      servicesRelationDto.setConsumer(ConsumerMapper.map(serviceRelation.getConsumer()));
    }
    servicesRelationDto.setErrorCount(serviceRelation.getCountOfError());
    servicesRelationDto.setWrongExpectationCount(serviceRelation.getCountOfErrorExpectation());
    return servicesRelationDto;
  }
}
