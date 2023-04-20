package com.hh.contractstestsadmin.model;


public class Service {

  private String name;

  private String version;

  private Boolean isConsumer;

  private String expectationLink;

  private String expectationPublishDate;

  private Boolean isProducer;

  private String schemaLink;

  private String schemaPublishDate;

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

  public Boolean getConsumer() {
    return isConsumer;
  }

  public void setConsumer(Boolean consumer) {
    isConsumer = consumer;
  }

  public String getExpectationLink() {
    return expectationLink;
  }

  public void setExpectationLink(String expectationLink) {
    this.expectationLink = expectationLink;
  }

  public String getExpectationPublishDate() {
    return expectationPublishDate;
  }

  public void setExpectationPublishDate(String expectationPublishDate) {
    this.expectationPublishDate = expectationPublishDate;
  }

  public Boolean getProducer() {
    return isProducer;
  }

  public void setProducer(Boolean producer) {
    isProducer = producer;
  }

  public String getSchemaLink() {
    return schemaLink;
  }

  public void setSchemaLink(String schemaLink) {
    this.schemaLink = schemaLink;
  }

  public String getSchemaPublishDate() {
    return schemaPublishDate;
  }

  public void setSchemaPublishDate(String schemaPublishDate) {
    this.schemaPublishDate = schemaPublishDate;
  }
}

