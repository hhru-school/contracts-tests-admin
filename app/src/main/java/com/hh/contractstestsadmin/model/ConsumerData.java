package com.hh.contractstestsadmin.model;

import java.net.URL;
import java.time.LocalDateTime;

public class ConsumerData  implements IContractsTestActorData {

  private final URL artifactURL;
  private final LocalDateTime artifactPublishDate;

  public ConsumerData(URL artifactURL, LocalDateTime artifactPublishDate) {
    this.artifactURL = artifactURL;
    this.artifactPublishDate = artifactPublishDate;
  }

  @Override
  public URL getArtifactURL() {
    return artifactURL;
  }

  @Override
  public LocalDateTime getArtifactPublishDate() {
    return artifactPublishDate;
  }
}
