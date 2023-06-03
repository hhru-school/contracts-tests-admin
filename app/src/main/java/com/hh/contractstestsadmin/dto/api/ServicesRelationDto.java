package com.hh.contractstestsadmin.dto.api;

public class ServicesRelationDto {

  private ProducerDto producer;

  private ConsumerDto consumer;

  private Long wrongExpectationCount;

  private Long errorCount;

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

  public Long getWrongExpectationCount() {
    return wrongExpectationCount;
  }

  public void setWrongExpectationCount(Long wrongExpectationCount) {
    this.wrongExpectationCount = wrongExpectationCount;
  }

  public Long getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(Long errorCount) {
    this.errorCount = errorCount;
  }
}
