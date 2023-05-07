package com.hh.contractstestsadmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "error_type")
public class ErrorType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "error_type_id")
  private Long id;
  @Column(name = "error_key")
  private String errorKey;

  @Column(name = "comments")
  private String comments;

  @OneToOne
  @JoinColumn(name = "error_id")
  private ContractTestError errorInfo;

  public ErrorType() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getErrorKey() {
    return errorKey;
  }

  public void setErrorKey(String error_key) {
    this.errorKey = error_key;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public ContractTestError getErrorInfo() {
    return errorInfo;
  }

  public void setErrorInfo(ContractTestError errorInfo) {
    this.errorInfo = errorInfo;
  }

}
