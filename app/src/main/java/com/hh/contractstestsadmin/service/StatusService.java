package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.ServiceDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StatusService {

    public StandsContainerDto getStands() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test-data/stand-container-exemple.json").getFile());
        return objectMapper.readValue(file, StandsContainerDto.class);
    }

    public List<ServiceDto> getServices(String standName) throws StandNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test-data/service-list-exemple.json").getFile());
        return objectMapper.readValue(file, new TypeReference<>(){});
    }

}
