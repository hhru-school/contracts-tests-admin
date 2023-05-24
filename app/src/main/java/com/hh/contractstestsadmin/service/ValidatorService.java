package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.validator.ValidationDto;
import com.hh.contractstestsadmin.exception.ValidatorException;
import java.io.IOException;
import java.io.InputStream;

public class ValidatorService {

  private final ObjectMapper objectMapper;

  public ValidatorService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public ValidationDto validate(String standName) throws ValidatorException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/full-validation-example.json");
    return objectMapper.readValue(inputStream, new TypeReference<>() {
    });
  }

}
