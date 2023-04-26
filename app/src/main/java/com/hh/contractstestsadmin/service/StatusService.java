package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dto.ServiceDto;
import com.hh.contractstestsadmin.dto.StandDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StatusService {

  private final ContractsDao contractsDao;

  private final String releaseName;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public StatusService(ContractsDao contractsDao, String releaseName) {
    this.contractsDao = contractsDao;
    this.releaseName = releaseName;
  }

  public StandsContainerDto getStands() throws ContractsDaoException {
    StandsContainerDto standsContainer = new StandsContainerDto();
    standsContainer.setReleaseName(releaseName);
    standsContainer.setStands(contractsDao
        .getStandNames()
        .stream()
        .sorted()
        .map(StandDto::new)
        .toList());
    return standsContainer;
  }

  public List<ServiceDto> getServices(String standName) throws StandNotFoundException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/service-list-exemple.json");
    return objectMapper.readValue(inputStream, new TypeReference<>() {
    });
  }

}
