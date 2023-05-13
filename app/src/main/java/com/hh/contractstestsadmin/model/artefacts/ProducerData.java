package com.hh.contractstestsadmin.model.artefacts;

import java.net.URL;
import java.time.LocalDateTime;

public record ProducerData(URL artifactURL, LocalDateTime artifactPublishDate) implements IContractsTestActorData {

}
