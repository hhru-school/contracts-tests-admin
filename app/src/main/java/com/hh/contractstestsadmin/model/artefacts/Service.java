package com.hh.contractstestsadmin.model.artefacts;

public class Service {

  private final String name;

  private final String version;

  private Artefact consumerData;

  private Artefact producerData;

  public Service(String name, String version) {
    this.name = name;
    this.version = version;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public Artefact getConsumerData() {
    return consumerData;
  }

  public void setConsumerData(Artefact consumerData) {
    this.consumerData = consumerData;
  }

  public Artefact getProducerData() {
    return producerData;
  }

  public void setProducerData(Artefact producerData) {
    this.producerData = producerData;
  }
}
