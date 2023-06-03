package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.api.ServiceDto;
import com.hh.contractstestsadmin.model.Service;
import java.util.Objects;

public class ServiceMapper {

  public static ServiceDto map(Service service) {
    Objects.requireNonNull(service);

    ServiceDto serviceDto = new ServiceDto();
    serviceDto.setId(service.getId());
    serviceDto.setName(service.getServiceName());
    serviceDto.setRelease(service.isRelease());
    serviceDto.setVersion(service.getTag());
    return serviceDto;
  }
}
