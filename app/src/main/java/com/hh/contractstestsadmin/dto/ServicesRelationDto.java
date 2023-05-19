package com.hh.contractstestsadmin.dto;

public class ServicesRelationDto {

  private ProducerDto producer;

  private ConsumerDto consumer;

  private Integer expectationCount;

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

  public Integer getExpectationCount() {
    return expectationCount;
  }

  public void setExpectationCount(Integer expectationCount) {
    this.expectationCount = expectationCount;
  }

  public Integer getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(Integer errorCount) {
    this.errorCount = errorCount;
  }
}
