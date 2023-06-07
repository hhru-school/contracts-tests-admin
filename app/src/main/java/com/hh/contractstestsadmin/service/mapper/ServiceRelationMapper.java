package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ServicesRelationDto;
import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.ServiceType;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ServiceRelationMapper {
  public static ServicesRelationDto map(ServiceRelation serviceRelation) {
    Objects.requireNonNull(serviceRelation);

    ServicesRelationDto servicesRelationDto = new ServicesRelationDto();
    if (serviceRelation.getProducer() != null) {
      Service producer = serviceRelation.getProducer();
      servicesRelationDto.setProducer(ServiceMapper.map(producer,
              URLEncoder.encode(producer.getSchemaLink(), StandardCharsets.UTF_8), ServiceType.PRODUCER
          )
      );
    }

    if (serviceRelation.getConsumer() != null) {
      Service consumer = serviceRelation.getConsumer();
      servicesRelationDto.setConsumer(ServiceMapper.map(consumer,
              URLEncoder.encode(consumer.getExpectationLink(), StandardCharsets.UTF_8), ServiceType.CONSUMER
          )
      );
    }
    servicesRelationDto.setErrorCount(serviceRelation.getErrorCount());
    servicesRelationDto.setFailedRequestPathCount(serviceRelation.getFailedRequestPathCount());
    return servicesRelationDto;
  }
}
