package com.hh.contractstestsadmin.validator.mapper;

import com.hh.contractstestsadmin.validator.dto.RequestDto;
import com.hh.contractstestsadmin.model.HttpMethod;
import ru.hh.test.contract.model.ExpectedRequest;

import java.util.HashMap;

public class ExpectedRequestMapper {

    public static RequestDto map(ExpectedRequest expectedRequest) {
        RequestDto requestDto = new RequestDto();
        requestDto.setMethod(HttpMethod.getMethodByName(expectedRequest.getMethod()));
        requestDto.setPath(expectedRequest.getPath());
        requestDto.setBody(requestDto.getBody());
        if (requestDto.getHeaders() != null) {
            requestDto.setHeaders(new HashMap<>(requestDto.getHeaders()));
        } else {
            requestDto.setHeaders(new HashMap<>());
        }
        if (requestDto.getQueryParams() != null) {
            requestDto.setQueryParams(new HashMap<>(requestDto.getQueryParams()));
        } else {
            requestDto.setQueryParams(new HashMap<>());
        }

        return requestDto;
    }
}
