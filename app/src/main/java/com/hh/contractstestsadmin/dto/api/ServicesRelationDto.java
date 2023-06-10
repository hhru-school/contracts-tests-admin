package com.hh.contractstestsadmin.dto.api;

public class ServicesRelationDto {

  private ServiceDto producer;

  private ServiceDto consumer;

  private Long failedRequestPathCount;

  private Long errorCount;

  public ServiceDto getProducer() {
    return producer;
  }

  public void setProducer(ServiceDto producer) {
    this.producer = producer;
  }

  public ServiceDto getConsumer() {
    return consumer;
  }

  public void setConsumer(ServiceDto consumer) {
    this.consumer = consumer;
  }

  public Long getFailedRequestPathCount() {
    return failedRequestPathCount;
  }

  public void setFailedRequestPathCount(Long failedRequestPathCount) {
    this.failedRequestPathCount = failedRequestPathCount;
  }

  public Long getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(Long errorCount) {
    this.errorCount = errorCount;
  }
}
