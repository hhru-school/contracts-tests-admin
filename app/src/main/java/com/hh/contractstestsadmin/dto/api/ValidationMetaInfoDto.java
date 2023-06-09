package com.hh.contractstestsadmin.dto.api;

import com.hh.contractstestsadmin.dto.ValidationStatus;
import java.time.OffsetDateTime;

public class ValidationMetaInfoDto {

  private Long id;

  private OffsetDateTime createdDate;

  private OffsetDateTime executeDate;

  private String releaseLink;

  private ValidationStatus status;

  private Integer errorCount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public OffsetDateTime getExecuteDate() {
    return executeDate;
  }

  public void setExecuteDate(OffsetDateTime executeDate) {
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
