package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dto.ServicesContainerDto;
import com.hh.contractstestsadmin.dto.StandInfoDto;
import com.hh.contractstestsadmin.dto.StandStatusDto;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.model.artefacts.Artefact;
import com.hh.contractstestsadmin.model.artefacts.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StatusService {

  private final StandsDao standsDao;

  private final String releaseName;

  private final ReleaseVersionDao releaseVersionDao;
  private final Map<String, Map<String, UUID>> filePathByStandName = new HashMap<>();

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
    List<Service> services = standsDao.getServices(releaseName);
    services.forEach(s -> fillArtefactIds(standName, s));
    servicesContainerDto.setRelease(
            services
                    .stream()
                    .map(ServiceStatusMapper::map)
                    .toList());
    if (!standName.equals(releaseName)) {
      services = standsDao.getServices(standName);
      servicesContainerDto.setStand(
              services
                      .stream()
                      .map(ServiceStatusMapper::map)
                      .toList());
    } else {
      servicesContainerDto.setStand(Collections.emptyList());
    }
    return servicesContainerDto;
  }

  private void fillArtefactIds(String standName, Service service) {
    filePathByStandName.putIfAbsent(standName, new HashMap<>());
    Map<String, UUID> fileIdByFilePath = filePathByStandName.get(standName);
    Artefact consumer = service.getConsumerData();
    if (consumer != null) {
      fileIdByFilePath.putIfAbsent(consumer.getArtefactUrl(), UUID.randomUUID());
      consumer.setFileId(fileIdByFilePath.get(consumer.getArtefactUrl()));
    }
    Artefact producer = service.getProducerData();
    if (producer != null) {
      fileIdByFilePath.putIfAbsent(producer.getArtefactUrl(), UUID.randomUUID());
      producer.setFileId(fileIdByFilePath.get(producer.getArtefactUrl()));
    }
  }

  public StandStatusDto getStatus(String standName) throws StandNotFoundException, StandsDaoException {
    StandStatusDto standStatusDto = new StandStatusDto();
    standStatusDto.setName(standName);
    standStatusDto.setIsRelease(standName.equals(releaseName));
    standStatusDto.setReleaseLink(releaseVersionDao.getCurrentReleaseVersion());
    standStatusDto.setServices(getServices(standName));
    return standStatusDto;
  }

  public String getSharedFileLink(String standName, UUID fileId) throws StandsDaoException {
    Map<String, UUID> filePathByFileId = filePathByStandName.get(standName);
    if (filePathByFileId == null) {
      throw new StandNotFoundException("not found stand with name " + standName);
    }
    for (Map.Entry<String, UUID> entry : filePathByFileId.entrySet()) {
      if (fileId.equals(entry.getValue())) {
        return standsDao.getArtefactUrl(standName, entry.getKey());
      }
    }
    throw new StandNotFoundException("not found fileId " + fileId + "for stand name " + standName);
  }
}
