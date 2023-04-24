package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dto.ServiceDto;
import com.hh.contractstestsadmin.dto.StandDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class StatusService {

    private final ContractsDao contractsDao;

    private String releaseName;

    public StatusService(ContractsDao contractsDao){
        this.contractsDao = contractsDao;
    }

    public void setReleaseName(String releaseName){
        this.releaseName = releaseName;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public StandsContainerDto getStands() throws ContractsDaoException {
        StandsContainerDto standsContainer = new StandsContainerDto();
        standsContainer.setReleaseName(releaseName);
        standsContainer.setStands(contractsDao
                .getStandNames()
                .orElseGet(Collections::emptyList)
                .stream()
                .map(StandDto::new)
                .toList());
        return standsContainer;
    }

    public List<ServiceDto> getServices(String standName) throws StandNotFoundException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("test-data/service-list-exemple.json");
        return objectMapper.readValue(inputStream, new TypeReference<>(){});
    }

}
