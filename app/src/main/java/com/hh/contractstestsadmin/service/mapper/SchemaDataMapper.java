package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.model.artefacts.Service;
import ru.hh.contract.validator.service.SchemaData;

public class SchemaDataMapper {

  public static SchemaData map(Service service, String schemaBody) {
    schemaBody = "info:\n" +
        "  title: Simple API overview\n" +
        "  version: 2.0.0\n" +
        "paths:\n" +
        "  /subscription/unsubscribe_link/1/applicant_digest:\n" +
        "    get:\n" +
        "      operationId: applicant_digest\n" +
        "      summary: applicant_digest\n" +
        "      responses:\n" +
        "        '200':\n" +
        "          description: |-\n" +
        "            200 response\n" +
        "          content:\n" +
        "            application/json: {\n" +
        "              \"id\": 1,\n" +
        "              \"entityId\": 1,\n" +
        "              \"unsubscribeUrl\": \"/unsubscribe\"\n" +
        "            }\n";
    return new SchemaData(schemaBody, service.getVersion());
  }
}
