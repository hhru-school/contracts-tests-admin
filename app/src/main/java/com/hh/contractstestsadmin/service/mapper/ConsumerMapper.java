package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ConsumerDto;
import com.hh.contractstestsadmin.model.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConsumerMapper {

  public static ConsumerDto map(Service service, String minioReleaseName) {
    ConsumerDto consumerDto = new ConsumerDto();
    consumerDto.setName(service.getServiceName());
    consumerDto.setId(service.getId());
    consumerDto.setVersion(service.getTag());
    consumerDto.setExpectationLink(URLEncoder.encode(service.getExpectationLink(), StandardCharsets.UTF_8));
    consumerDto.setRelease(minioReleaseName.equals(service.getStandName()));
    return consumerDto;
  }
}
