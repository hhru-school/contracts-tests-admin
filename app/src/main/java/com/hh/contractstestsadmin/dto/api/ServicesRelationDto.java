package com.hh.contractstestsadmin.dto.api;

public class ServicesRelationDto {

  private ProducerDto producer;

  private ConsumerDto consumer;

  private Integer wrongExpectationCount;

  private Integer errorCount;

  public ProducerDto getProducer() {
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
