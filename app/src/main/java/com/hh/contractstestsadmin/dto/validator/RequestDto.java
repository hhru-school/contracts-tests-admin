package com.hh.contractstestsadmin.dto.validator;

import com.hh.contractstestsadmin.model.HttpMethod;
import java.util.List;
import java.util.Map;

public class RequestDto {

  private HttpMethod method;

  private String path;

  private String body;

  private Map<String, List<String>> headers;

  private Map<String, List<String>> queryParams;

}
