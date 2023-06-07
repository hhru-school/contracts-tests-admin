package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ServiceDto;
import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.model.ServiceType;
import java.util.Objects;

public class ServiceMapper {

  public static ServiceDto map(Service service, String fileLink, ServiceType serviceType) {
    Objects.requireNonNull(service);
    ServiceDto serviceDto = new ServiceDto();
    serviceDto.setId(service.getId());
    serviceDto.setName(service.getServiceName());
    serviceDto.setRelease(service.isRelease());
    serviceDto.setVersion(service.getTag());
    serviceDto.setFileLink(fileLink);
    serviceDto.setServiceType(serviceType);
    return serviceDto;
  }
}
