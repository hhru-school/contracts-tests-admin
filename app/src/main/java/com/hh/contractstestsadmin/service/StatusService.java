package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dto.ServiceDto;
import com.hh.contractstestsadmin.dto.StandInfoDto;
import com.hh.contractstestsadmin.dto.StandStatusDto;
import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StatusService {

  private final ContractsDao contractsDao;

  private final ValidationService validationService;

  private final String releaseName;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public StatusService(ContractsDao contractsDao, String releaseName, ValidationService validationService) {
    this.contractsDao = contractsDao;
    this.releaseName = releaseName;
    this.validationService = validationService;
  }

  public List<StandInfoDto> getStands() throws ContractsDaoException {
    return contractsDao
        .getStandNames()
        .stream()
        .sorted()
        .map((standName) -> new StandInfoDto(standName, standName.equals(releaseName)))
        .toList();
  }

  public List<ServiceDto> getServices(String standName) throws StandNotFoundException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("test-data/service-list-exemple.json");
    return objectMapper.readValue(inputStream, new TypeReference<>() {
    });
  }

  public StandStatusDto getStatus(String standName, Long sizeLimit) throws StandNotFoundException, IOException {
    StandStatusDto standStatusDto = new StandStatusDto();
    standStatusDto.setReleaseServices(getServices(releaseName));
    if(!standName.equals(releaseName)){
      standStatusDto.setStandServices(getServices(standName));
      standStatusDto.setIsRelease(false);
    }
    else {
      standStatusDto.setIsRelease(true);
    }
    standStatusDto.setHistoryPreview(validationService.getValidationsHistory(standName,sizeLimit));
    return standStatusDto;
  }

}
