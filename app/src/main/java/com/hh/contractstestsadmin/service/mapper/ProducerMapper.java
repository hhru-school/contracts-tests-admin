package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ProducerDto;
import com.hh.contractstestsadmin.model.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ProducerMapper {
  public static ProducerDto map(Service service, String minioReleaseName) {
    ProducerDto producerDto = new ProducerDto();
    producerDto.setId(service.getId());
    producerDto.setName(service.getServiceName());
    producerDto.setVersion(service.getTag());
    producerDto.setSchemaLink(URLEncoder.encode(service.getSchemaLink(), StandardCharsets.UTF_8));
    producerDto.setRelease(minioReleaseName.equals(service.getStandName()));
    return producerDto;
  }
}
