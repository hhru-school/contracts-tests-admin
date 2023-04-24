package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.ServiceDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StatusService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public StandsContainerDto getStands() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("test-data/stand-container-exemple.json");
        return objectMapper.readValue(inputStream, StandsContainerDto.class);
    }

    public List<ServiceDto> getServices(String standName) throws StandNotFoundException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("test-data/service-list-exemple.json");
        return objectMapper.readValue(inputStream, new TypeReference<>(){});
    }

}
