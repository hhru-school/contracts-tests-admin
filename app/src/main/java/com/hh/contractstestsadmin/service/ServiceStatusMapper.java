package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ServiceStatusDto;
import com.hh.contractstestsadmin.model.artefacts.Service;

public class ServiceStatusMapper {

  public static ServiceStatusDto map(Service service) {
    ServiceStatusDto serviceStatusDto = new ServiceStatusDto();
    serviceStatusDto.setName(service.getName());
    serviceStatusDto.setVersion(service.getVersion());
    service.getConsumerData().ifPresentOrElse(
        (consumerData) -> {
          serviceStatusDto.setIsConsumer(true);
          serviceStatusDto.setExpectationLink(consumerData.artefactURL());
          serviceStatusDto.setExpectationPublishDate(consumerData.artefactPublishDate());
        },
        () -> serviceStatusDto.setIsConsumer(false)
    );
    service.getProducerData().ifPresentOrElse(
        (producerData) -> {
          serviceStatusDto.setIsProducer(true);
          serviceStatusDto.setSchemaLink(producerData.artefactURL());
          serviceStatusDto.setSchemaPublishDate(producerData.artefactPublishDate());
        },
        () -> serviceStatusDto.setIsProducer(false)
    );
    return serviceStatusDto;
  }

}
