package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.validator.WrongExpectationDto;
import ru.hh.contract.validator.dto.ContractValidationResultDto;

public class WrongExpectationMapper {

  public static WrongExpectationDto map(ContractValidationResultDto contractValidationResultDto) {
    return new WrongExpectationDto();
  }
}
