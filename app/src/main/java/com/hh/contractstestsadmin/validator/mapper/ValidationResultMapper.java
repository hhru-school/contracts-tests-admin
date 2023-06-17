package com.hh.contractstestsadmin.validator.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.validator.dto.ValidationDto;
import com.hh.contractstestsadmin.validator.dto.WrongExpectationDto;
import java.util.List;
import java.util.stream.Collectors;
import ru.hh.contract.validator.dto.ContractValidationResultDto;

public class ValidationResultMapper {

  public static ValidationDto map(
      List<ContractValidationResultDto> validationResults,
      ObjectMapper objectMapper
  ) throws JsonProcessingException {
    List<WrongExpectationDto> wrongExpectationDtos = validationResults
        .stream()
        .map(WrongExpectationMapper::map)
        .collect(Collectors.toList());
    ValidationDto validationDto = new ValidationDto();
    validationDto.setValidatorReport(objectMapper.writeValueAsString(validationResults));
    validationDto.setWrongExpectationsDto(wrongExpectationDtos);
    return validationDto;
  }
}
