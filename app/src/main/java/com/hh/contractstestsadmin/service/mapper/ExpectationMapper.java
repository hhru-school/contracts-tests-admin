package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ErrorDto;
import com.hh.contractstestsadmin.dto.api.ExpectationDto;
import com.hh.contractstestsadmin.model.Expectation;
import java.util.List;

public class ExpectationMapper {
  public static ExpectationDto mapFromEntity(Expectation expectation) {
    ExpectationDto expectationDto = new ExpectationDto();
    expectationDto.setId(expectation.getId());
    expectationDto.setHttpMethod(expectation.getHttpMethod());
    expectation.setRequestPath(expectation.getRequestPath());
    expectationDto.setRequestHeaders(expectation.getRequestHeaders());
    expectationDto.setQueryParams(expectation.getQueryParams());
    expectationDto.setRequestBody(expectation.getRequestBody());
    expectationDto.setResponseStatus(expectation.getResponseStatus());
    expectationDto.setResponseHeaders(expectation.getResponseHeaders());
    expectationDto.setResponseBody(expectation.getResponseBody());

    List<ErrorDto> errors = expectation.getContractTestErrors().stream()
        .map(ContractTestErrorMapper::mapFromEntity)
        .toList();
    expectationDto.setErrors(errors);

    return expectationDto;
  }
}
