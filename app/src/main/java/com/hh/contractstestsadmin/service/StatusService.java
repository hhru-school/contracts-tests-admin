package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
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

  private final ReleaseVersionDao releaseVersionDao;

  private final ObjectMapper objectMapper;

  public StatusService(ContractsDao contractsDao, String releaseName, ReleaseVersionDao releaseVersionDao, ObjectMapper objectMapper) {
    this.contractsDao = contractsDao;
    this.releaseName = releaseName;
    this.releaseVersionDao = releaseVersionDao;
    this.objectMapper = objectMapper;
  }

  public List<StandInfoDto> getStands(String search) throws ContractsDaoException {
    return contractsDao
        .getStandNames()
        .stream()
        .filter(standName -> search == null || standName.contains(search))
        .sorted()
        .map((standName) -> new StandInfoDto(standName, standName.equals(releaseName)))
        .toList();
  }

  public ServicesContainerDto getServices(String standName) throws StandNotFoundException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/service-list-exemple.json");
    return objectMapper.readValue(inputStream, ServicesContainerDto.class);
  }

  public StandStatusDto getStatus(String standName) throws StandNotFoundException, IOException {
    StandStatusDto standStatusDto = new StandStatusDto();
    standStatusDto.setName(standName);
    standStatusDto.setIsRelease(standName.equals(releaseName));
    standStatusDto.setReleaseLink(releaseVersionDao.getCurrentReleaseVersion());
    standStatusDto.setServices(getServices(standName));
    return standStatusDto;
  }

}
