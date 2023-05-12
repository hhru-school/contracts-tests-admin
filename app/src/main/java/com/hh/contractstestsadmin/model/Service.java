package com.hh.contractstestsadmin.model;

import com.hh.contractstestsadmin.exception.StandsDaoException;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public class Service {

  private final String name;

  private final String version;

  private Optional<ConsumerData> consumerData;

  private Optional<ProducerData> producerData;

  public Service(
      String name,
      String version,
      @NotNull ConsumerData consumerData
  ) {
    this.name = name;
    this.version = version;
    this.consumerData = Optional.of(consumerData);
    this.producerData = Optional.empty();
  }

  public Service(
      String name,
      String version,
      ProducerData producerData
  ) {
    this.name = name;
    this.version = version;
    this.producerData = Optional.of(producerData);
    this.consumerData = Optional.empty();
  }

  public void setConsumerData(@NotNull ConsumerData consumerData) throws StandsDaoException {
    if (this.consumerData.isEmpty()) {
      this.consumerData = Optional.of(consumerData);
    } else {
      throw new StandsDaoException("Attempt to set consumer data twice");
    }
  }

  public Optional<ConsumerData> getConsumerData() {
    return consumerData;
  }

  public void setProducerData(@NotNull ProducerData consumerData) throws StandsDaoException {
    if (this.producerData.isEmpty()) {
      this.producerData = Optional.of(consumerData);
    } else {
      throw new StandsDaoException("Attempt to set producer data twice");
    }
  }

  public Optional<ProducerData> getProducerData() {
    return producerData;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }
}
