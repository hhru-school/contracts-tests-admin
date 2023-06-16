package com.hh.contractstestsadmin.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.model.Expectation;
import com.hh.contractstestsadmin.model.artefacts.Service;
import java.util.List;
import ru.hh.contract.validator.service.ExpectationsData;
import ru.hh.test.contract.model.Expectations;

public class ExpectationsDataMapper {

  public static ExpectationsData map(Service service, String expectationsString, ObjectMapper objectMapper) throws JsonProcessingException {
    expectationsString = "{\n" +
        "  \"serviceName\": \"jlogic\",\n" +
        "  \"expectations\": [{\n" +
        "    \"expectedRequest\": {\n" +
        "      \"method\": \"GET\",\n" +
        "      \"path\": \"subscription/unsubscribe_link/1/applicant_digest\",\n" +
        "      \"body\": null,\n" +
        "      \"headers\": {},\n" +
        "      \"queryParams\": {}        \n" +
        "    },\n" +
        "    \"expectedResponse\": {\n" +
        "      \"status\": 200,\n" +
        "      \"headers\": {\n" +
        "        \"Content-Type\": [\"application/json\"]\n" +
        "      },\n" +
        "      \"responseBody\": \"{\\\"id\\\": 2,\\\"entityId\\\": 1,\\\"unsubscribeUrl\\\": \\\"/unsubscribe\\\"}\"\n" +
        "    },\n" +
        "    \"expectedResponse\": {\n" +
        "      \"status\": 200,\n" +
        "      \"headers\": {\n" +
        "        \"Content-Type\": [\"application/json\"]\n" +
        "      },\n" +
        "      \"responseBody\": \"{\\\"id\\\": 1,\\\"unsubscribeUrl\\\":\\\"/unsubscribe\\\"}\"\n" +
        "    },\n" +
        "    \"producerName\": \"subscriptions\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"expectedRequest\": {\n" +
        "      \"method\": \"POST\",\n" +
        "      \"path\": \"/subscription/externalemail/\",\n" +
        "      \"body\": null,\n" +
        "      \"headers\": {},\n" +
        "      \"queryParams\": {}\n" +
        "    },\n" +
        "    \"expectedResponse\": { \"status\":200, \"headers\": {}, \"responseBody\": null },\n" +
        "    \"producerName\": \"subscriptions\" \n" +
        "  }]\n" +
        "}\n";
    Expectations expectations = objectMapper.readValue(expectationsString, new TypeReference<>(){});
    return new ExpectationsData(expectations, service.getVersion());
  }
}
