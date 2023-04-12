package com.hh.contractstestsadmin.dto;

public class ContractsInfoDto {

    private Long serviceId;

    private Integer warningsCount;

    private ExpectationsInfoDto expectations;

    private SchemeInfoDto scheme;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getWarningsCount() {
        return warningsCount;
    }

    public void setWarningsCount(Integer warningsCount) {
        this.warningsCount = warningsCount;
    }

    public ExpectationsInfoDto getExpectations() {
        return expectations;
    }

    public void setExpectations(ExpectationsInfoDto expectations) {
        this.expectations = expectations;
    }

    public SchemeInfoDto getScheme() {
        return scheme;
    }

    public void setScheme(SchemeInfoDto scheme) {
        this.scheme = scheme;
    }
}
