package com.hh.contractstestsadmin.dto.validator;

import java.util.List;

public class WrongExpectationDto {

  private String producerName;

  private String producerVersion;

  private Boolean producerIsRelease;

  private String consumerName;

  private String consumerVersion;

  private Boolean consumerIsRelease;

  private RequestDto request;

  private ResponseDto response;

  private List<MessageDto> messages;

  public String getProducerName() {
    return producerName;
  }

  public void setProducerName(String producerName) {
    this.producerName = producerName;
  }

  public String getProducerVersion() {
    return producerVersion;
  }

  public void setProducerVersion(String producerVersion) {
    this.producerVersion = producerVersion;
  }

  public Boolean getProducerIsRelease() {
    return producerIsRelease;
  }

  public void setProducerIsRelease(Boolean producerIsRelease) {
    this.producerIsRelease = producerIsRelease;
  }

  public String getConsumerName() {
    return consumerName;
  }

  public void setConsumerName(String consumerName) {
    this.consumerName = consumerName;
  }

  public String getConsumerVersion() {
    return consumerVersion;
  }

  public void setConsumerVersion(String consumerVersion) {
    this.consumerVersion = consumerVersion;
  }

  public Boolean getConsumerIsRelease() {
    return consumerIsRelease;
  }

  public void setConsumerIsRelease(Boolean consumerIsRelease) {
    this.consumerIsRelease = consumerIsRelease;
  }

  public RequestDto getRequest() {
    return request;
  }

  public void setRequest(RequestDto request) {
    this.request = request;
  }

  public ResponseDto getResponse() {
    return response;
  }

  public void setResponse(ResponseDto response) {
    this.response = response;
  }

  public List<MessageDto> getMessages() {
    return messages;
  }

  public void setMessages(List<MessageDto> messages) {
    this.messages = messages;
  }
}
