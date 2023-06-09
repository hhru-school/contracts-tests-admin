package com.hh.contractstestsadmin.dto.api;

public class ServicesRelationDto {

  private ProducerDto producer;

  private ConsumerDto consumer;

  private Long failedRequestPathCount;

  private Long errorCount;

  public ServiceDto getProducer() {
    return producer;
  }

  public void setProducer(ProducerDto producer) {
    this.producer = producer;
  }

  public ConsumerDto getConsumer() {
    return consumer;
  }

  public void setConsumer(ConsumerDto consumer) {
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
