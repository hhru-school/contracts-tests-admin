package com.hh.contractstestsadmin.dto.validator;

import java.util.List;
import java.util.Map;

public class ResponseDto {

  private Integer status;

  private Map<String, List<String> > headers;

  private String body;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Map<String, List<String> > getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, List<String> > headers) {
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}