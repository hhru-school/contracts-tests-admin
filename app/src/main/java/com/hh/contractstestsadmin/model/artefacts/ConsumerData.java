package com.hh.contractstestsadmin.model.artefacts;

import java.time.OffsetDateTime;

public record ConsumerData(String artefactURL, OffsetDateTime artefactPublishDate) implements IArtefactData {

}
