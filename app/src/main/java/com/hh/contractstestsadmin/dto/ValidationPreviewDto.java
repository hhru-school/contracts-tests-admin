package com.hh.contractstestsadmin.dto;

public class ValidationPreviewDto {

    private Long id;

    private String date;

    private ValidationStatus status;

    private Integer errorCount;

    private Integer totalWarningsCount;

    private Integer localWarningsCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public void setStatus(ValidationStatus status) {
        this.status = status;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getTotalWarningsCount() {
        return totalWarningsCount;
    }

    public void setTotalWarningsCount(Integer totalWarningsCount) {
        this.totalWarningsCount = totalWarningsCount;
    }

    public Integer getLocalWarningsCount() {
        return localWarningsCount;
    }

    public void setLocalWarningsCount(Integer localWarningsCount) {
        this.localWarningsCount = localWarningsCount;
    }
}
