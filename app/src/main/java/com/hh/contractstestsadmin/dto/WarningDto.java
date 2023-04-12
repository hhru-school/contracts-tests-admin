package com.hh.contractstestsadmin.dto;

public class WarningDto {

    private Long id;

    private WarningType type;

    private Long producerServiceId;

    private String producerServiceName;

    private String deprecatedOperationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WarningType getType() {
        return type;
    }

    public void setType(WarningType type) {
        this.type = type;
    }

    public Long getProducerServiceId() {
        return producerServiceId;
    }

    public void setProducerServiceId(Long producerServiceId) {
        this.producerServiceId = producerServiceId;
    }

    public String getProducerServiceName() {
        return producerServiceName;
    }

    public void setProducerServiceName(String producerServiceName) {
        this.producerServiceName = producerServiceName;
    }

    public String getDeprecatedOperationId() {
        return deprecatedOperationId;
    }

    public void setDeprecatedOperationId(String deprecatedOperationId) {
        this.deprecatedOperationId = deprecatedOperationId;
    }
}
