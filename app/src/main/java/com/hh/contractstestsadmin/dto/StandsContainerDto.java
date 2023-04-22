package com.hh.contractstestsadmin.dto;

import java.util.List;

public class StandsContainerDto {

    private String releaseName;

    private String releaseLink;

    private List<StandDto> stands;

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public String getReleaseLink() {
        return releaseLink;
    }

    public void setReleaseLink(String releaseLink) {
        this.releaseLink = releaseLink;
    }

    public List<StandDto> getStands() {
        return stands;
    }

    public void setStands(List<StandDto> stands) {
        this.stands = stands;
    }
}
