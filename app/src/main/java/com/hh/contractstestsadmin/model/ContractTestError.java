package com.hh.contractstestsadmin.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Table(name = "error")
@TypeDefs({
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
})
@Entity
public class ContractTestError {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "error_info_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private ErrorType errorType;
  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  @JoinColumn(name = "error_level")
  private ErrorLevel level;

  @ManyToOne(fetch = FetchType.LAZY)
  Expectation expectation;

  public ContractTestError() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long errorInfoId) {
    this.id = errorInfoId;
  }

  public ErrorLevel getLevel() {
    return level;
  }

  public void setLevel(ErrorLevel level) {
    this.level = level;
  }

  public Expectation getExpectation() {
    return expectation;
  }

  public void setExpectation(Expectation expectation) {
    this.expectation = expectation;
  }
  public ErrorType getErrorType() {
    return errorType;
  }

  public void setErrorType(ErrorType errorType) {
    this.errorType = errorType;
  }
}
