package com.hh.contractstestsadmin.model.artefacts;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Artefact {
    private UUID fileId;
    private String artefactUrl;
    private OffsetDateTime artefactPublishDate;

    public Artefact(String artefactURL, OffsetDateTime artefactPublishDate) {
        this.artefactUrl = artefactURL;
        this.artefactPublishDate = artefactPublishDate;
    }

    public String getArtefactUrl() {
        return artefactUrl;
    }

    public void setArtefactUrl(String artefactUrl) {
        this.artefactUrl = artefactUrl;
    }

    public OffsetDateTime getArtefactPublishDate() {
        return artefactPublishDate;
    }

    public void setArtefactPublishDate(OffsetDateTime artefactPublishDate) {
        this.artefactPublishDate = artefactPublishDate;
    }

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }
}
