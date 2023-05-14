package com.hh.contractstestsadmin.model;

public class ErrorProducerConsumerLink {
    private String producerName;
    private String consumerName;

    public ErrorProducerConsumerLink() {
    }

    public ErrorProducerConsumerLink(String producerName, String consumerName) {
        this.producerName = producerName;
        this.consumerName = consumerName;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }
}
