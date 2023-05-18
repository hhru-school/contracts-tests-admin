package com.hh.contractstestsadmin.model;

public class ServiceRelation {
    private Service producer;
    private Service consumer;
    private long countErrorByEndpoint;
    private long countOfError;

    public ServiceRelation() {
    }

    public ServiceRelation(Service producer, Service consumer, Long countErrorByEndpoint,
                           Long countOfError) {
        this.producer = producer;
        this.consumer = consumer;
        this.countErrorByEndpoint = countErrorByEndpoint == null ? 0 : countErrorByEndpoint;
        this.countOfError = countOfError == null ? 0 : countOfError;
    }

    public ServiceRelation(Service producer, Service consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    public Service getProducer() {
        return producer;
    }

    public void setProducer(Service producer) {
        this.producer = producer;
    }

    public Service getConsumer() {
        return consumer;
    }

    public void setConsumer(Service consumer) {
        this.consumer = consumer;
    }

    public long getCountErrorByEndpoint() {
        return countErrorByEndpoint;
    }

    public void setCountErrorByEndpoint(long countErrorByEndpoint) {
        this.countErrorByEndpoint = countErrorByEndpoint;
    }

    public long getCountOfError() {
        return countOfError;
    }

    public void setCountOfError(long countOfError) {
        this.countOfError = countOfError;
    }
}
