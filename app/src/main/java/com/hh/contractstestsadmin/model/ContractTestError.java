package com.hh.contractstestsadmin.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Table(name = "error")
@TypeDefs({
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
public class ContractTestError {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "error_info_id")
  private Long id;
  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "consumer_id")
  private Service consumer;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "producer_id")
  private Service producer;

  @ManyToOne(fetch = FetchType.LAZY)
  private ErrorType errorType;

  @Type(type = "jsonb")
  @Column(name = "error", columnDefinition = "jsonb")
  private ValidatorError error;

  @Column(name = "http_method")
  private String httpMethod;

  @Column(name = "request_patch")
  private String requestPatch;

  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  @JoinColumn(name = "error_level")
  private  ErrorLevel level;

  @ManyToOne(fetch = FetchType.LAZY)
  Expectation expectation;

  @ManyToOne(fetch = FetchType.LAZY)
  private Validation validation;

  public ContractTestError() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long errorInfoId) {
    this.id = errorInfoId;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getRequestPatch() {
    return requestPatch;
  }

  public void setRequestPatch(String requestPatch) {
    this.requestPatch = requestPatch;
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

  public ValidatorError getError() {
    return error;
  }

  public void setError(ValidatorError error) {
    this.error = error;
  }

  public Service getConsumer() {
    return consumer;
  }

  public void setConsumer(Service consumer) {
    this.consumer = consumer;
  }

  public Service getProducer() {
    return producer;
  }

  public void setProducer(Service producer) {
    this.producer = producer;
  }

  public Validation getValidation() {
    return validation;
  }

  public void setValidation(Validation validation) {
    this.validation = validation;
  }

  public ErrorType getErrorType() {
    return errorType;
  }

  public void setErrorType(ErrorType errorType) {
    this.errorType = errorType;
  }
}
