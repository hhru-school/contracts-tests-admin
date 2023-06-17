package com.hh.contractstestsadmin.validator.mapper;

import com.hh.contractstestsadmin.validator.dto.RequestDto;
import com.hh.contractstestsadmin.model.HttpMethod;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ru.hh.test.contract.model.ExpectedRequest;

import java.util.HashMap;

public class ExpectedRequestMapper {

    public static RequestDto map(ExpectedRequest expectedRequest) {
        RequestDto requestDto = new RequestDto();
        requestDto.setMethod(HttpMethod.getMethodByName(expectedRequest.getMethod()));
        requestDto.setPath(expectedRequest.getPath());
        requestDto.setBody(requestDto.getBody());
        Map<String, List<String>> headers = Optional.ofNullable(expectedRequest.getHeaders())
            .orElse(new HashMap<>());
        requestDto.setHeaders(headers);
        Map<String, List<String>> queryParams = Optional.ofNullable(expectedRequest.getQueryParams())
            .orElse(new HashMap<>());
        requestDto.setQueryParams(queryParams);

        return requestDto;
    }
}
