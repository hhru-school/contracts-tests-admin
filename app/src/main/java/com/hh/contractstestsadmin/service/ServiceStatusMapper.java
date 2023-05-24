package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.api.ServiceStatusDto;
import com.hh.contractstestsadmin.model.artefacts.Service;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

public class ServiceStatusMapper {

  public static ServiceStatusDto map(Service service) {
    ServiceStatusDto serviceStatusDto = new ServiceStatusDto();
    serviceStatusDto.setName(service.getName());
    serviceStatusDto.setVersion(service.getVersion());
    service.getConsumerData().ifPresentOrElse(
        (consumerData) -> {
          serviceStatusDto.setIsConsumer(true);
          serviceStatusDto.setExpectationLink(URLEncoder.encode(consumerData.artefactURL(), StandardCharsets.UTF_8));
          serviceStatusDto.setExpectationPublishDate(consumerData.artefactPublishDate());
        },
        () -> serviceStatusDto.setIsConsumer(false)
    );
    service.getProducerData().ifPresentOrElse(
        (producerData) -> {
          serviceStatusDto.setIsProducer(true);
          serviceStatusDto.setSchemaLink(URLEncoder.encode(producerData.artefactURL(), StandardCharsets.UTF_8));
          serviceStatusDto.setSchemaPublishDate(producerData.artefactPublishDate());
        },
        () -> serviceStatusDto.setIsProducer(false)
    );
    return serviceStatusDto;
  }
}
