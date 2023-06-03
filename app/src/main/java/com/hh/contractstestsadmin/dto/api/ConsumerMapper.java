package com.hh.contractstestsadmin.dto.api;

import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.service.ServiceMapper;

public class ConsumerMapper {

  public static ConsumerDto map(Service service) {
    ServiceDto serviceDto = ServiceMapper.map(service);
    ConsumerDto consumerDto = new ConsumerDto(serviceDto);
    consumerDto.setExpectationLink(service.getExpectationLink());
    return consumerDto;
  }
}
