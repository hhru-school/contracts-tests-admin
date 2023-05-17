package com.hh.contractstestsadmin.model.artefacts;

import java.time.LocalDateTime;

public record ConsumerData(String artefactURL, LocalDateTime artefactPublishDate) implements IArtefactData {

}
