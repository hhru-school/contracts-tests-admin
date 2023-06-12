package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.model.artefacts.Service;
import ru.hh.contract.validator.service.ExpectationsData;
import ru.hh.contract.validator.service.SchemaData;
import ru.hh.test.contract.model.Expectations;

public class SchemaDataMapper {

  public static SchemaData map(Service service, String schemaBody) {
    return new SchemaData(schemaBody, service.getVersion());
  }
}
