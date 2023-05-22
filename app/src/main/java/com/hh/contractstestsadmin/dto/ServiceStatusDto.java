package com.hh.contractstestsadmin.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ServiceStatusDto {

  private String name;

  private String version;

  private Boolean isConsumer;

  private UUID consumerId;

  private String expectationLink;

  private OffsetDateTime expectationPublishDate;

  private Boolean isProducer;

  private String schemaLink;

  private UUID producerId;

  private OffsetDateTime schemaPublishDate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean getIsConsumer() {
    return isConsumer;
  }

  public void setIsConsumer(Boolean consumer) {
    isConsumer = consumer;
  }

  public String getExpectationLink() {
    return expectationLink;
  }

  public void setExpectationLink(String expectationLink) {
    this.expectationLink = expectationLink;
  }

  public OffsetDateTime getExpectationPublishDate() {
    return expectationPublishDate;
  }

  public void setExpectationPublishDate(OffsetDateTime expectationPublishDate) {
    this.expectationPublishDate = expectationPublishDate;
  }

  public boolean getIsProducer() {
    return isProducer;
  }

  public void setIsProducer(Boolean producer) {
    isProducer = producer;
  }

  public String getSchemaLink() {
    return schemaLink;
  }

  public void setSchemaLink(String schemaLink) {
    this.schemaLink = schemaLink;
  }

  public OffsetDateTime getSchemaPublishDate() {
    return schemaPublishDate;
  }

  public UUID getConsumerId() {
    return consumerId;
  }

  public void setConsumerId(UUID consumerId) {
    this.consumerId = consumerId;
  }

  public UUID getProducerId() {
    return producerId;
  }

  public void setProducerId(UUID producerId) {
    this.producerId = producerId;
  }

  public void setSchemaPublishDate(OffsetDateTime schemaPublishDate) {
    this.schemaPublishDate = schemaPublishDate;
  }
}
