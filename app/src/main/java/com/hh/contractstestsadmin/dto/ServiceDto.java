package com.hh.contractstestsadmin.dto;

public class ServiceDto {

    private String name;

    private String version;

    private Boolean isConsumer;

    private String expectationLink;

    private String expectationPublishDate;

    private Boolean isProducer;

    private  String schemaLink;

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

    public String getExpectationPublishDate() {
        return expectationPublishDate;
    }

    public void setExpectationPublishDate(String expectationPublishDate) {
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

    public String getSchemaPublishDate() {
        return schemaPublishDate;
    }

    public void setSchemaPublishDate(String schemaPublishDate) {
        this.schemaPublishDate = schemaPublishDate;
    }
}
