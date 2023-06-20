package com.hh.contractstestsadmin.validator.mapper;

import com.hh.contractstestsadmin.model.artefacts.Service;
import ru.hh.contract.validator.service.SchemaData;

public class SchemaDataMapper {

  public static SchemaData map(Service service, String schemaString) {
    return new SchemaData(schemaString, service.getVersion());
  }

}
