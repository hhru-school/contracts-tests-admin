package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dto.ServicesContainerDto;
import com.hh.contractstestsadmin.dto.StandDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import com.hh.contractstestsadmin.exception.StandsDaoException;
import java.io.IOException;
import java.io.InputStream;

public class StatusService {

  private final StandsDao standsDao;

  private final String releaseName;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public StatusService(StandsDao standsDao, String releaseName) {
    this.standsDao = standsDao;
    this.releaseName = releaseName;
  }

  public StandsContainerDto getStands() throws StandsDaoException {
    StandsContainerDto standsContainer = new StandsContainerDto();
    standsContainer.setReleaseName(releaseName);
    standsContainer.setStands(standsDao
        .getStandNames()
        .stream()
        .sorted()
        .map(StandDto::new)
        .toList());
    return standsContainer;
  }

  public ServicesContainerDto getServices(String standName) throws StandNotFoundException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/service-list-exemple.json");
    return objectMapper.readValue(inputStream, new TypeReference<>() {
    });
  }

}
