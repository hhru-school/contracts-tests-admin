package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.model.artefacts.Service;
import ru.hh.contract.validator.service.ExpectationsData;
import ru.hh.test.contract.model.Expectations;

public class ExpectationsDataMapper {

  public static ExpectationsData map(Service service, String expectations) {
    return new ExpectationsData(new Expectations(), service.getVersion());
  }
}
