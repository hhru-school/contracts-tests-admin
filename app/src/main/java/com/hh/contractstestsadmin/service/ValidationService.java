package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ValidationService {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public List<ValidationPreviewDto> getValidationHistory(
      String standName,
      Long sizeLimit
  ) throws ValidationHistoryNotFoundException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/validation-preview-list-exemple.json");
    List<ValidationPreviewDto> result = objectMapper.readValue(inputStream, new TypeReference<>() {});
    if (sizeLimit == null) {
      return result;
    }
    return result.stream().limit(sizeLimit).toList();
  }

  public void runValidation(String standName) throws StandNotFoundException {

  }
}
