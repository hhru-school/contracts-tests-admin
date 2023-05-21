package com.hh.contractstestsadmin.dto;

import com.hh.contractstestsadmin.model.HttpMethod;
import java.util.List;

public class ExpectationDto {

  private Long id;

  private HttpMethod httpMethod;

  private String requestPath;

  private List<EntryDto> requestHeaders;

  private List<EntryDto> queryParams;

  private String requestBody;

  private String curlRequest;

  private Integer responseStatus;

  private List<EntryDto> responseHeaders;

  private String responseBody;

  private List<ErrorDto> errors;

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

  public String getCurlRequest() {
    return curlRequest;
  }

  public void setCurlRequest(String curlRequest) {
    this.curlRequest = curlRequest;
  }

  public Integer getResponseStatus() {
    return responseStatus;
  }

  public void setResponseStatus(Integer responseStatus) {
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

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }

  public List<ErrorDto> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorDto> errors) {
    this.errors = errors;
  }
}
