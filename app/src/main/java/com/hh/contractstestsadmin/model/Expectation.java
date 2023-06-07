package com.hh.contractstestsadmin.model;

import com.hh.contractstestsadmin.dto.api.EntryDto;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Tuple;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@TypeDefs({
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
}
)
@Table(name = "expectation")
public class Expectation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "expectation_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "consumer_id")
  private Service consumer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "producer_id")
  private Service producer;

  @Type(type = "pgsql_enum")
  @Enumerated(EnumType.STRING)
  @Column(name = "http_method")
  private HttpMethod httpMethod;

  @Column(name = "request_path")
  private String requestPath;

  @Type(type = "jsonb")
  @Column(name = "request_headers", columnDefinition = "jsonb")
  private List<EntryDto> requestHeaders = new ArrayList<>();

  @Type(type = "jsonb")
  @Column(name = "query_params", columnDefinition = "jsonb")
  private List<EntryDto> queryParams = new ArrayList<>();

  @Column(name = "request_body")
  private String requestBody;

  @Column(name = "response_status")
  private int responseStatus;

  @Type(type = "jsonb")
  @Column(name = "response_headers", columnDefinition = "jsonb")
  private List<EntryDto> responseHeaders = new ArrayList<>();

  @Column(name = "response_body")
  private String responseBody;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "validation_id")
  private Validation validation;

  @OneToMany(mappedBy = "expectation", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<ContractTestError> contractTestErrors = new ArrayList<>();


  public Expectation() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getRequestPath() {
    return requestPath;
  }

  public void setRequestPath(String requestPath) {
    this.requestPath = requestPath;
  }

  public List<EntryDto> getRequestHeaders() {
    return requestHeaders;
  }

  public void setRequestHeaders(List<EntryDto> requestHeaders) {
    this.requestHeaders = requestHeaders;
  }

  public List<EntryDto> getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(List<EntryDto> queryParams) {
    this.queryParams = queryParams;
  }

  public String getRequestBody() {
    return requestBody;
  }

  public void setRequestBody(String requestBody) {
    this.requestBody = requestBody;
  }

  public int getResponseStatus() {
    return responseStatus;
  }

  public void setResponseStatus(int responseStatus) {
    this.responseStatus = responseStatus;
  }

  public List<EntryDto> getResponseHeaders() {
    return responseHeaders;
  }

  public void setResponseHeaders(List<EntryDto> responseHeaders) {
    this.responseHeaders = responseHeaders;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public List<ContractTestError> getContractTestErrors() {
    return contractTestErrors;
  }

  public void setContractTestErrors(List<ContractTestError> contractTestErrors) {
    this.contractTestErrors = contractTestErrors;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
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
}
