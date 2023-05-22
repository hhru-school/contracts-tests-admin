package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ServiceStatusDto;
import com.hh.contractstestsadmin.model.artefacts.Service;

import java.util.Optional;

public class ServiceStatusMapper {

  public static ServiceStatusDto map(Service service) {
    ServiceStatusDto serviceStatusDto = new ServiceStatusDto();
    serviceStatusDto.setName(service.getName());
    serviceStatusDto.setVersion(service.getVersion());
    Optional.ofNullable(service.getConsumerData()).ifPresentOrElse(
        (consumerData) -> {
          serviceStatusDto.setConsumerId(consumerData.getFileId());
          serviceStatusDto.setIsConsumer(true);
          serviceStatusDto.setExpectationLink(consumerData.getArtefactUrl());
          serviceStatusDto.setExpectationPublishDate(consumerData.getArtefactPublishDate());
        },
        () -> serviceStatusDto.setIsConsumer(false)
    );
    Optional.ofNullable(service.getProducerData()).ifPresentOrElse(
        (producerData) -> {
          serviceStatusDto.setConsumerId(producerData.getFileId());
          serviceStatusDto.setIsProducer(true);
          serviceStatusDto.setSchemaLink(producerData.getArtefactUrl());
          serviceStatusDto.setSchemaPublishDate(producerData.getArtefactPublishDate());
        },
        () -> serviceStatusDto.setIsProducer(false)
    );
    return serviceStatusDto;
  }

}
