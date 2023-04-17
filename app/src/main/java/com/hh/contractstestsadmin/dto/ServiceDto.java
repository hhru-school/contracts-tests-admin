package com.hh.contractstestsadmin.dto;

public class ServiceDto {

    private String id;

    private String version;

    private String jiraLink;

    private Boolean isConsumer;

    private String expectationLink;

    private String expectationPublishDate;

    private Boolean isProducer;

    private  String schemaLink;

    private String schemaPublishDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJiraLink() {
        return jiraLink;
    }

    public void setJiraLink(String jiraLink) {
        this.jiraLink = jiraLink;
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
