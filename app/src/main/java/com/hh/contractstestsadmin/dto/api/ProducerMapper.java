package com.hh.contractstestsadmin.dto.api;

import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.service.ServiceMapper;

public class ProducerMapper {
  public static ProducerDto map(Service service) {
    ServiceDto serviceDto = ServiceMapper.map(service);
    ProducerDto producerDto = new ProducerDto(serviceDto);
    producerDto.setSchemaLink(service.getSchemaLink());
    return producerDto;
  }
}
