package com.hh.contractstestsadmin.validator.dto;

import com.hh.contractstestsadmin.model.HttpMethod;
import java.util.Map;

public class RequestDto {

  private HttpMethod method;

  private String path;

  private String body;

  private Map<String, String> headers;

  private Map<String, String> queryParams;

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(Map<String, String> queryParams) {
    this.queryParams = queryParams;
  }
}
