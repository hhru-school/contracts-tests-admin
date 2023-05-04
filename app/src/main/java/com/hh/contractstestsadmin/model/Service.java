package com.hh.contractstestsadmin.model;

import java.net.URL;
import java.time.Instant;

public class Service {

  private final String name;

  private final String version;

  private final Boolean isConsumer;

  private final URL expectationLink;

  private final Instant expectationPublishDate;

  private final Boolean isProducer;

  private final URL schemaLink;

  private final Instant schemaPublishDate;

  public Service(
      String name,
      String version,
      Boolean isConsumer,
      URL expectationLink,
      Instant expectationPublishDate,
      Boolean isProducer,
      URL schemaLink,
      Instant schemaPublishDate
  ) {
    this.name = name;
    this.version = version;
    this.isConsumer = isConsumer;
    this.expectationLink = expectationLink;
    this.expectationPublishDate = expectationPublishDate;
    this.isProducer = isProducer;
    this.schemaLink = schemaLink;
    this.schemaPublishDate = schemaPublishDate;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public Boolean getConsumer() {
    return isConsumer;
  }

  public URL getExpectationLink() {
    return expectationLink;
  }

  public Instant getExpectationPublishDate() {
    return expectationPublishDate;
  }

  public Boolean getProducer() {
    return isProducer;
  }

  public URL getSchemaLink() {
    return schemaLink;
  }

  public Instant getSchemaPublishDate() {
    return schemaPublishDate;
  }

}
