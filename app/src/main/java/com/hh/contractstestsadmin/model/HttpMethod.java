package com.hh.contractstestsadmin.model;

import java.util.Arrays;
import java.util.Objects;

public enum HttpMethod {
    GET, POST, DELETE, PUT, HEAD, PATCH, OPTIONS, NOT_DEFINED;

    public static HttpMethod getMethodByName(String name) {
        Objects.requireNonNull(name);
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(name.toUpperCase()))
                .findFirst()
                .orElse(NOT_DEFINED);
    }
}
