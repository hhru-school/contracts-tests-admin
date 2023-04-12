package com.hh.contractstestsadmin.dto;

import java.util.List;

public class SchemeInfoDto {

    private Boolean isValid;

    private Integer dependentServicesCount;

    private String publishDate;

    private Integer warningsCount;

    private List<WarningDto> warningsList;

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Integer getDependentServicesCount() {
        return dependentServicesCount;
    }

    public void setDependentServicesCount(Integer dependentServicesCount) {
        this.dependentServicesCount = dependentServicesCount;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getWarningsCount() {
        return warningsCount;
    }

    public void setWarningsCount(Integer warningsCount) {
        this.warningsCount = warningsCount;
    }

    public List<WarningDto> getWarningsList() {
        return warningsList;
    }

    public void setWarningsList(List<WarningDto> warningsList) {
        this.warningsList = warningsList;
    }
}
