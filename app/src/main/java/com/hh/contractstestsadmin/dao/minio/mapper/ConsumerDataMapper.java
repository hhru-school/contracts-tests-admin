package com.hh.contractstestsadmin.dao.minio.mapper;

import com.hh.contractstestsadmin.model.artefacts.Artefact;
import io.minio.messages.Item;

public class ConsumerDataMapper {

  public Artefact map(Item artefact, String artefactUrl) {
    return new Artefact(artefactUrl, artefact.lastModified().toOffsetDateTime());
  }

}

