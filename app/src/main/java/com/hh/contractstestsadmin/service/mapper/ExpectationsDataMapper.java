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
        "  \"serviceName\" : \"jlogic\",\n" +
        "  \"expectations\" : [ {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":12300," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":false,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.testAddUtmParamsToLinkUrl()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":true,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldNotSendIfAppWasUsedAndBothOfUserFiltersAreEnabled()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":true,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.testAddUtmAndParamsWithoutUserIdToHhResearchLinkUrlWhenThereIsNoUserId()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 599,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"null\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldNotSendIfEmptyDevice()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":true,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldNotSendIfAppWasUsedAndFilterIsForNeverUsedByRegisteredUserOnly()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":false,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldNotSendIfAppWasNotUsedAndFilterIsForUsedByRegisteredUserOnly()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":false,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldSendIfAppWasNotUsedAndFilterIsForNeverUsedByRegisteredUserOnly()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":false,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldSendIfAppWasNotUsedAndUserFilterNotSpecify()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":12300," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":true,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.testAddUtmAndUserIdParamsToLinkUrl()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":true,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.testForLinkWithoutHost()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":false,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldSendIfAppWasNotUsedAndBothOfUserFiltersAreEnabled()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":true,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldSendIfAppWasUsedAndUserFilterNotSpecify()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  }, {\n" +
        "    \"expectedRequest\" : {\n" +
        "      \"method\" : \"GET\",\n" +
        "      \"path\" : \"/device/123\",\n" +
        "      \"body\" : null,\n" +
        "      \"headers\" : {\n" +
        "        \"Accept\" : [ \"application/json\" ],\n" +
        "        \"X-Outer-Timeout-Ms\" : [ \"0\" ]\n" +
        "      },\n" +
        "      \"queryParams\" : { }\n" +
        "    },\n" +
        "    \"expectedResponse\" : {\n" +
        "      \"status\" : 200,\n" +
        "      \"headers\" : {\n" +
        "        \"Content-Type\" : [ \"application/json\" ]\n" +
        "      },\n" +
        "      \"responseBody\" : \"{\\\"deviceId\\\":123,\\\"deviceToken\\\":null,\\\"registrationTime\\\":null,\\\"lastVisitTime\\\":null," +
        "\\\"active\\\":true,\\\"deviceOsType\\\":null,\\\"deviceMode\\\":null,\\\"ownerUserId\\\":null," +
        "\\\"atLeastOnceUsedByRegisteredUser\\\":true,\\\"uuid\\\":null}\"\n" +
        "    },\n" +
        "    \"producerName\" : \"mobile-notifier\",\n" +
        "    \"testInfo\" : \"SendOpenLinkPushToDeviceTest.shouldSendIfAppWasUsedAndFilterIsForUsedByRegisteredUserOnly()\",\n" +
        "    \"minimalRequired\" : false\n" +
        "  } ]\n" +
        "}";
    Expectations expectations = objectMapper.readValue(expectationsString, new TypeReference<>(){});
    return new ExpectationsData(expectations, service.getVersion());
  }
}
