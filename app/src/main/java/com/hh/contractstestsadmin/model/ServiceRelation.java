package com.hh.contractstestsadmin.model;

public class ServiceRelation {
    private Service producer;
    private Service consumer;
    private long failedRequestPathCount;
    private long countOfError;

    public ServiceRelation() {
    }

    public ServiceRelation(Service producer, Service consumer, Long failedRequestPathCount,
                           Long countOfError) {
        this.producer = producer;
        this.consumer = consumer;
        this.failedRequestPathCount = failedRequestPathCount == null ? 0 : failedRequestPathCount;
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

    public long getFailedRequestPathCount() {
        return failedRequestPathCount;
    }

    public void setFailedRequestPathCount(long failedRequestPathCount) {
        this.failedRequestPathCount = failedRequestPathCount;
    }

    public long getCountOfError() {
        return countOfError;
    }

    public void setCountOfError(long countOfError) {
        this.countOfError = countOfError;
    }
}
