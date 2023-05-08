package com.hh.contractstestsadmin.dto;

public class ValidationPreviewDto {

  private Long id;

  private String createdDate;

  private String executeDate;

  private String releaseLink;

  private ValidationStatus status;

  private Integer errorCount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getExecuteDate() {
    return executeDate;
  }

  public void setExecuteDate(String executeDate) {
    this.executeDate = executeDate;
  }

  public String getReleaseLink() {
    return releaseLink;
  }

  public void setReleaseLink(String releaseLink) {
    this.releaseLink = releaseLink;
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

}
