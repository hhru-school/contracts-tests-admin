package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.ErrorDto;
import com.hh.contractstestsadmin.dto.api.ExpectationDto;
import com.hh.contractstestsadmin.model.Expectation;
import com.hh.contractstestsadmin.model.Service;
import com.hh.contractstestsadmin.validator.dto.MessageDto;
import com.hh.contractstestsadmin.validator.dto.WrongExpectationDto;
import java.util.List;

public class ExpectationMapper {
  public static ExpectationDto mapFromEntity(Expectation expectation) {
    ExpectationDto expectationDto = new ExpectationDto();
    expectationDto.setId(expectation.getId());
    expectationDto.setHttpMethod(expectation.getHttpMethod());
    expectationDto.setRequestPath(expectation.getRequestPath());
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

  public static Expectation mapToEntity(WrongExpectationDto wrongExpectation, String minioReleaseName, String standName) {
    Expectation expectation = new Expectation();
    expectation.linkWithConsumer(
        new Service(
            wrongExpectation.getConsumerName(),
            wrongExpectation.getConsumerIsRelease() ? minioReleaseName : standName,
            wrongExpectation.getConsumerVersion()
        )
    );
    expectation.linkWithProducer(
        new Service(
            wrongExpectation.getProducerName(),
            wrongExpectation.getProducerIsRelease() ? minioReleaseName : standName,
            wrongExpectation.getProducerVersion()
        )
    );
    expectation.setHttpMethod(wrongExpectation.getRequest().getMethod());
    expectation.setRequestPath(wrongExpectation.getRequest().getPath());
    expectation.setRequestHeaders(wrongExpectation.getRequest().getHeaders());
    expectation.setQueryParams(wrongExpectation.getRequest().getQueryParams());
    expectation.setRequestBody(wrongExpectation.getRequest().getBody());
    expectation.setResponseStatus(wrongExpectation.getResponse().getStatus());
    expectation.setResponseHeaders(wrongExpectation.getResponse().getHeaders());
    expectation.setResponseBody(wrongExpectation.getResponse().getBody());
    for (MessageDto message : wrongExpectation.getMessages()) {
      expectation.addContractTestError(ContractTestErrorMapper.mapToEntity(message));
    }
    return expectation;
  }

}
