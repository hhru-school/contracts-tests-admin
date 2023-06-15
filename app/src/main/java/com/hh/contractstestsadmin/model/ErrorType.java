package com.hh.contractstestsadmin.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.Hibernate;

@Entity
@Table(name = "error_type")
public class ErrorType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "error_type_id")
  private Long id;
  @Column(name = "error_key")
  private String errorKey;

  @Column(name = "comment")
  private String comment;

  @OneToMany(mappedBy = "errorType", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<ContractTestError> contractTests = new HashSet<>();

  public ErrorType() {
  }

  public ErrorType(String errorKey) {
    this.errorKey = errorKey;
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Set<ContractTestError> getContractTests() {
    return contractTests;
  }

  public void setContractTests(Set<ContractTestError> contractTests) {
    this.contractTests = contractTests;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ErrorType that = (ErrorType) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
