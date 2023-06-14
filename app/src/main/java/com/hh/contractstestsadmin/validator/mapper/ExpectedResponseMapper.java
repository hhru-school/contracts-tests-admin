package com.hh.contractstestsadmin.validator.mapper;

import com.hh.contractstestsadmin.dto.validator.ResponseDto;
import ru.hh.test.contract.model.ExpectedResponse;

import java.util.HashMap;

public class ExpectedResponseMapper {

    public static ResponseDto map(ExpectedResponse expectedResponse) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(expectedResponse.getStatus());
        responseDto.setHeaders(new HashMap<>(expectedResponse.getHeaders()));
        responseDto.setBody((String) expectedResponse.getResponseBody());
        return responseDto;
    }
}
