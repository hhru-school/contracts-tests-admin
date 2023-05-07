package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dto.ServicesContainerDto;
import com.hh.contractstestsadmin.dto.StandInfoDto;
import com.hh.contractstestsadmin.dto.StandStatusDto;
import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kotlin.collections.EmptyList;

public class StatusService {

  private final ContractsDao contractsDao;

  private final String releaseName;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public StatusService(ContractsDao contractsDao, String releaseName) {
    this.contractsDao = contractsDao;
    this.releaseName = releaseName;
  }

  public List<StandInfoDto> getStands() throws ContractsDaoException {
    return contractsDao
        .getStandNames()
        .stream()
        .sorted()
        .map((standName) -> new StandInfoDto(standName, standName.equals(releaseName)))
        .toList();
  }

  public ServicesContainerDto getServices(String standName) throws StandNotFoundException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/service-list-exemple.json");
    return objectMapper.readValue(inputStream, new TypeReference<>() {
    });
  }

  public StandStatusDto getStatus(String standName) throws StandNotFoundException, IOException {
    StandStatusDto standStatusDto = new StandStatusDto();
    standStatusDto.setName(standName);
    standStatusDto.setIsRelease(standName.equals(releaseName));
    standStatusDto.setServices(getServices(standName));
    return standStatusDto;
  }

}
