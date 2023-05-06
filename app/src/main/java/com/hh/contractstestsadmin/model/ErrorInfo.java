package com.hh.contractstestsadmin.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Table(name = "error_info")
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
public class ErrorInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "error_ifo_id")
  private Long errorInfoId;
  @Column(name = "error_key")
  private String errorKey;

  @Column(name = "comment")
  private String comment;

  @Type(type = "jsonb")
  @Column(name = "error", columnDefinition = "jsonb")
  private ValidatorError error;

  @ManyToOne(fetch = FetchType.LAZY)
  private ValidationInfo validationInfo;

  public ErrorInfo() {
  }

  public Long getErrorInfoId() {
    return errorInfoId;
  }

  public void setErrorInfoId(Long errorInfoId) {
    this.errorInfoId = errorInfoId;
  }

  public String getErrorKey() {
    return errorKey;
  }

  public void setErrorKey(String errorKey) {
    this.errorKey = errorKey;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public ValidatorError getError() {
    return error;
  }

  public void setError(ValidatorError error) {
    this.error = error;
  }

  public ValidationInfo getValidationInfo() {
    return validationInfo;
  }

  public void setValidationInfo(ValidationInfo validationInfo) {
    this.validationInfo = validationInfo;
  }
}
