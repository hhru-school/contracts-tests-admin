package com.hh.contractstestsadmin.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.model.Expectation;
import com.hh.contractstestsadmin.model.artefacts.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import ru.hh.contract.validator.service.ExpectationsData;
import ru.hh.test.contract.model.Expectations;

public class ExpectationsDataMapper {

  public static ExpectationsData map(Service service, String expectationsString, ObjectMapper objectMapper) throws IOException {
    Expectations expectations = objectMapper.readValue(expectationsString, new TypeReference<>(){});
    return new ExpectationsData(expectations, service.getVersion());
  }
}
