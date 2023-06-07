package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dto.api.ServiceStatusDto;
import com.hh.contractstestsadmin.dto.api.ServicesContainerDto;
import com.hh.contractstestsadmin.dto.api.StandInfoDto;
import com.hh.contractstestsadmin.dto.api.StandStatusDto;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import com.hh.contractstestsadmin.service.mapper.ServiceStatusMapper;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatusService {

  private final StandsDao standsDao;

  private final String releaseName;

  private final ReleaseVersionDao releaseVersionDao;

  public StatusService(StandsDao standsDao, String releaseName, ReleaseVersionDao releaseVersionDao) {
    this.standsDao = standsDao;
    this.releaseName = releaseName;
    this.releaseVersionDao = releaseVersionDao;
  }

  public List<StandInfoDto> getStands(String search) throws StandsDaoException {
    return standsDao
        .getStandNames()
        .stream()
        .filter(standName -> search == null || standName.contains(search))
        .sorted()
        .map((standName) -> new StandInfoDto(standName, standName.equals(releaseName)))
        .toList();
  }

  public ServicesContainerDto getServices(String standName) throws StandNotFoundException, StandsDaoException {
    ServicesContainerDto servicesContainerDto = new ServicesContainerDto();
    servicesContainerDto.setRelease(
        standsDao
            .getServices(releaseName)
            .stream()
            .map(ServiceStatusMapper::map)
            .sorted(Comparator.comparing(ServiceStatusDto::getName))
            .toList());
    if (!standName.equals(releaseName)) {
      servicesContainerDto.setStand(
          standsDao
              .getServices(standName)
              .stream()
              .map(ServiceStatusMapper::map)
              .sorted(Comparator.comparing(ServiceStatusDto::getName))
              .toList());
    } else {
      servicesContainerDto.setStand(Collections.emptyList());
    }
    return servicesContainerDto;
  }

  public StandStatusDto getStatus(String standName) throws StandNotFoundException, StandsDaoException {
    StandStatusDto standStatusDto = new StandStatusDto();
    standStatusDto.setName(standName);
    standStatusDto.setIsRelease(standName.equals(releaseName));
    standStatusDto.setReleaseLink(releaseVersionDao.getCurrentReleaseVersion());
    standStatusDto.setServices(getServices(standName));
    return standStatusDto;
  }

}
