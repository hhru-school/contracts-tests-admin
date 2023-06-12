package com.hh.contractstestsadmin.dto.api;

import com.hh.contractstestsadmin.model.HttpMethod;
import java.util.List;
import java.util.Map;

public class ExpectationDto {

  private Long id;

  private HttpMethod httpMethod;

  private String requestPath;

  private Map<String, List<String> > requestHeaders;

  private Map<String, List<String> > queryParams;

  private String requestBody;

  private String curlRequest;

  private Integer responseStatus;

  private Map<String, List<String> > responseHeaders;

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

  public Map<String, List<String> > getRequestHeaders() {
    return requestHeaders;
  }

  public void setRequestHeaders(Map<String, List<String> > requestHeaders) {
    this.requestHeaders = requestHeaders;
  }

  public Map<String, List<String> > getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(Map<String, List<String> > queryParams) {
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

  public Map<String, List<String> > getResponseHeaders() {
    return responseHeaders;
  }

  public void setResponseHeaders(Map<String, List<String> > responseHeaders) {
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
