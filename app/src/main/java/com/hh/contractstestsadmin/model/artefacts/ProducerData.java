package com.hh.contractstestsadmin.model.artefacts;

import java.time.OffsetDateTime;

public record ProducerData(String artefactURL, OffsetDateTime artefactPublishDate) implements IArtefactData {

}
