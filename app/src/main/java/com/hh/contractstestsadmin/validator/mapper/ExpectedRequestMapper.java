package com.hh.contractstestsadmin.validator.mapper;

import com.hh.contractstestsadmin.dto.validator.RequestDto;
import com.hh.contractstestsadmin.model.HttpMethod;
import ru.hh.test.contract.model.ExpectedRequest;

import java.util.HashMap;

public class ExpectedRequestMapper {

    public static RequestDto map(ExpectedRequest expectedRequest) {
        RequestDto requestDto = new RequestDto();
        requestDto.setMethod(HttpMethod.getMethodByName(expectedRequest.getMethod()));
        requestDto.setPath(expectedRequest.getPath());
        requestDto.setBody(requestDto.getBody());
        requestDto.setHeaders(new HashMap<>(requestDto.getHeaders()));
        requestDto.setQueryParams(new HashMap<>(requestDto.getQueryParams()));

        return requestDto;
    }
}
