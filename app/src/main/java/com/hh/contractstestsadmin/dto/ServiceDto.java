package com.hh.contractstestsadmin.dto;

public class ServiceDto {

    private Long id;

    private String name;

    private String version;

    private String jiraLink;

    private ServiceType type;

    private Boolean isConsumer;

    private Boolean isProducer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getJiraLink() {
        return jiraLink;
    }

    public void setJiraLink(String jiraLink) {
        this.jiraLink = jiraLink;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public Boolean getConsumer() {
        return isConsumer;
    }

    public void setConsumer(Boolean consumer) {
        isConsumer = consumer;
    }

    public Boolean getProducer() {
        return isProducer;
    }

    public void setProducer(Boolean producer) {
        isProducer = producer;
    }
}
