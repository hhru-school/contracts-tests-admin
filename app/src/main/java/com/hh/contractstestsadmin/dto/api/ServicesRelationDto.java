package com.hh.contractstestsadmin.dto.api;

public class ServicesRelationDto {

  private ServiceDto producer;

  private ServiceDto consumer;

  private Integer wrongExpectationCount;

  private Integer errorCount;

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

  public Integer getWrongExpectationCount() {
    return wrongExpectationCount;
  }

  public void setWrongExpectationCount(Integer wrongExpectationCount) {
    this.wrongExpectationCount = wrongExpectationCount;
  }

  public Integer getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(Integer errorCount) {
    this.errorCount = errorCount;
  }
}
