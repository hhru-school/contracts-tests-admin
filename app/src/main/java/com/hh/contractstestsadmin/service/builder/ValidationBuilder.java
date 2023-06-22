package com.hh.contractstestsadmin.service.builder;

import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.api.ConsumerDto;
import com.hh.contractstestsadmin.dto.api.ProducerDto;
import com.hh.contractstestsadmin.dto.api.ServicesRelationDto;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.Validation;
import com.hh.contractstestsadmin.model.artefacts.ArtefactType;
import com.hh.contractstestsadmin.service.mapper.ValidationMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ValidationBuilder {

  private final StandsDao standsDao;

  private final String minioReleaseName;

  public ValidationBuilder(StandsDao standsDao, String minioReleaseName) {
    this.standsDao = standsDao;
    this.minioReleaseName = minioReleaseName;
  }

  public ValidationWithRelationsDto buildValidationWithRelationsDto(Validation validation, List<ServiceRelation> serviceRelations) {
    ValidationWithRelationsDto validationWithRelationsDto = new ValidationWithRelationsDto(ValidationMapper.map(validation));
    validationWithRelationsDto.setServicesRelations(
        serviceRelations.stream()
            .map(this::buildServicesRelationDto)
            .toList()
    );

    return validationWithRelationsDto;
  }

  private ServicesRelationDto buildServicesRelationDto(ServiceRelation serviceRelation) {
    ServicesRelationDto servicesRelationDto = new ServicesRelationDto();

    if (serviceRelation.getProducer() != null) {
      servicesRelationDto.setProducer(buildProducerDto(serviceRelation.getProducer()));
    }

    if (serviceRelation.getConsumer() != null) {
      servicesRelationDto.setConsumer(buildConsumerDto(serviceRelation.getConsumer()));
    }

    servicesRelationDto.setErrorCount(serviceRelation.getErrorCount());
    servicesRelationDto.setFailedRequestPathCount(serviceRelation.getFailedRequestPathCount());
    return servicesRelationDto;
  }

  private ConsumerDto buildConsumerDto(Service service) {
    ConsumerDto consumerDto = new ConsumerDto();
    consumerDto.setName(service.getServiceName());
    consumerDto.setId(service.getId());
    consumerDto.setVersion(service.getTag());
    consumerDto.setExpectationLink(buildArtefactLink(service, ArtefactType.EXPECTATION));
    consumerDto.setRelease(minioReleaseName.equals(service.getStandName()));
    return consumerDto;
  }

  private ProducerDto buildProducerDto(Service service) {
    ProducerDto producerDto = new ProducerDto();
    producerDto.setName(service.getServiceName());
    producerDto.setId(service.getId());
    producerDto.setVersion(service.getTag());
    producerDto.setSchemaLink(buildArtefactLink(service, ArtefactType.SCHEMA));
    producerDto.setRelease(minioReleaseName.equals(service.getStandName()));
    return producerDto;
  }

  private String buildArtefactLink(Service service, ArtefactType artefactType) {
    return URLEncoder.encode(
        service.getStandName() + "/" + standsDao.buildArtefactPath(
            service.getServiceName(),
            service.getTag(),
            artefactType
        ),
        StandardCharsets.UTF_8
    );
  }
}
