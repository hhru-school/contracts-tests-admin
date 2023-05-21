package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.exception.ValidatorException;
import com.hh.contractstestsadmin.dto.validator.WrongExpectationDto;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ValidatorService {

  private final ObjectMapper objectMapper;

  public ValidatorService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public List<WrongExpectationDto> validate(String standName) throws ValidatorException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/full-validation-example.json");
    return objectMapper.readValue(inputStream, new TypeReference<>() {
    });
  }

}
