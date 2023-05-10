package com.hh.contractstestsadmin.model;

import java.net.URL;
import java.time.LocalDateTime;

public record ConsumerData(URL artifactURL, LocalDateTime artifactPublishDate) implements IContractsTestActorData {

}
