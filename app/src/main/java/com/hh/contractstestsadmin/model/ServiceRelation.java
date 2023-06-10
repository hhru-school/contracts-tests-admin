package com.hh.contractstestsadmin.model;

public class ServiceRelation {
    private Service producer;
    private Service consumer;
    private long failedRequestPathCount;
    private long errorCount;

    public ServiceRelation() {
    }

    public ServiceRelation(Service producer, Service consumer, Long failedRequestPathCount,
                           Long errorCount
    ) {
        this.producer = producer;
        this.consumer = consumer;
        this.failedRequestPathCount = failedRequestPathCount == null ? 0 : failedRequestPathCount;
        this.errorCount = errorCount == null ? 0 : errorCount;
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

    public long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }
}
