package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ServicesRelationDto;
import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.ServiceType;
import java.util.Objects;

public class ServiceRelationMapper {
  public static ServicesRelationDto map(ServiceRelation serviceRelation) {
    Objects.requireNonNull(serviceRelation);

    ServicesRelationDto servicesRelationDto = new ServicesRelationDto();
    if (serviceRelation.getProducer() != null) {
      Service producer = serviceRelation.getProducer();
      servicesRelationDto.setProducer(
          ServiceMapper.map(producer, producer.getSchemaLink(), ServiceType.PRODUCER)
      );
    }

    if (serviceRelation.getConsumer() != null) {
      Service consumer = serviceRelation.getConsumer();
      servicesRelationDto.setConsumer(
          ServiceMapper.map(consumer, consumer.getExpectationLink(), ServiceType.CONSUMER)
      );
    }
    servicesRelationDto.setErrorCount(serviceRelation.getErrorCount());
    servicesRelationDto.setFailedRequestPathCount(serviceRelation.getFailedRequestPathCount());
    return servicesRelationDto;
  }
}
