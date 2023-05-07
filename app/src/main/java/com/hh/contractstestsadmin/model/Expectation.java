package com.hh.contractstestsadmin.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@TypeDefs(
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
)
@Table(name = "expectation")
public class Expectation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "expectation_id")
  private Long id;

  @Type(type = "pgsql_enum")
  @Column(name = "http_method")
  private HttpMethod httpMethod;

  @Column(name = "request_path")
  private String requestPath;

  @Column(name = "request_headers")
  private String requestHeaders;

  @Column(name = "query_params")
  private String queryParams;

  @Column(name = "request_body")
  private String requestBody;

  @Column(name = "response_status")
  private int responseStatus;

  @Column(name = "response_headers")
  private String responseHeaders;

  @Column(name = "response_body")
  private String responseBody;

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

  public String getRequestHeaders() {
    return requestHeaders;
  }

  public void setRequestHeaders(String requestHeaders) {
    this.requestHeaders = requestHeaders;
  }

  public String getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(String queryParams) {
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

  public String getResponseHeaders() {
    return responseHeaders;
  }

  public void setResponseHeaders(String responseHeaders) {
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
}
