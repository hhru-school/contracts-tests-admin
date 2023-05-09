package com.hh.contractstestsadmin.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

  @OneToMany(mappedBy = "errorType", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<ContractTestError> contractTests = new ArrayList<>();

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

  public List<ContractTestError> getContractTests() {
    return contractTests;
  }

  public void setContractTests(List<ContractTestError> contractTests) {
    this.contractTests = contractTests;
  }
}