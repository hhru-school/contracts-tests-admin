package com.hh.contractstestsadmin.validator.mapper;

import com.hh.contractstestsadmin.validator.dto.ResponseDto;
import joptsimple.internal.Strings;
import ru.hh.test.contract.model.ExpectedResponse;

import java.util.HashMap;

public class ExpectedResponseMapper {

    public static ResponseDto map(ExpectedResponse expectedResponse) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(expectedResponse.getStatus());
        if (expectedResponse.getHeaders() != null) {
            responseDto.setHeaders(new HashMap<>(expectedResponse.getHeaders()));
        } else {
            responseDto.setHeaders(new HashMap<>());
        }
        if (expectedResponse.getResponseBody() != null) {
            responseDto.setBody((String) expectedResponse.getResponseBody());
        } else {
            responseDto.setBody(Strings.EMPTY);
        }
        return responseDto;
    }
}
