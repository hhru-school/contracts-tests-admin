package com.hh.contractstestsadmin.validator.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.model.artefacts.Service;
import ru.hh.contract.validator.service.ExpectationsData;
import ru.hh.test.contract.model.Expectations;

public class ExpectationsDataMapper {

  public static ExpectationsData map(Service service, String expectationsString, ObjectMapper objectMapper) throws JsonProcessingException {
    Expectations expectations = objectMapper.readValue(expectationsString, Expectations.class);
    return new ExpectationsData(expectations, service.getVersion());
  }
}
