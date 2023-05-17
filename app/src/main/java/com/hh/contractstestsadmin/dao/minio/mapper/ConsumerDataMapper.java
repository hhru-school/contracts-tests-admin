package com.hh.contractstestsadmin.dao.minio.mapper;

import com.hh.contractstestsadmin.model.artefacts.ConsumerData;
import io.minio.messages.Item;

public class ConsumerDataMapper {

  public ConsumerData map(Item artefact, String artefactUrl) {
    return new ConsumerData(artefactUrl, artefact.lastModified().toLocalDateTime());
  }

}

