package com.hh.contractstestsadmin.service.util;

import com.hh.contractstestsadmin.dao.ServiceDao;
import com.hh.contractstestsadmin.model.artefacts.Service;

public class ServicesContextManager {

  private final ServiceDao serviceDao;

  public ServicesContextManager(ServiceDao serviceDao) {
    this.serviceDao = serviceDao;
  }

  public Service getOrCreateService(String serviceName, String standName, String version){
    return null;
  }

}
