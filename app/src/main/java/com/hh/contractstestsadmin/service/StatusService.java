package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.ServiceDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StatusService {

    public StandsContainerDto getStands(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("stand-container-exemple.json").getFile());
            return objectMapper.readValue(file, StandsContainerDto.class);
        } catch (Exception e){
            return new StandsContainerDto();
        }
    }

    public List<ServiceDto> getServices(String standName) throws StandNotFoundException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("service-list-exemple.json").getFile());
            return objectMapper.readValue(file, new TypeReference<>(){});
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

}
