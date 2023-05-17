package com.hh.contractstestsadmin.model.artefacts;

import java.time.LocalDateTime;

public record ProducerData(String artefactURL, LocalDateTime artefactPublishDate) implements IArtefactData {

}
