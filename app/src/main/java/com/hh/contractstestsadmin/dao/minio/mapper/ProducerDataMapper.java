package com.hh.contractstestsadmin.dao.minio.mapper;

import com.hh.contractstestsadmin.model.artefacts.ProducerData;
import io.minio.messages.Item;

public class ProducerDataMapper {

  public ProducerData map(Item artefact, String artefactUrl) {
    return new ProducerData(artefactUrl, artefact.lastModified().toLocalDateTime());
  }

}

