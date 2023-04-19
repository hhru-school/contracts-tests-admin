package com.hh.contractstestsadmin.dto;

import java.util.List;

public class StandsContainerDto {

    private String releaseName;

    private String releaseVersion;

    private List<StandDto> stands;

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public List<StandDto> getStands() {
        return stands;
    }

    public void setStands(List<StandDto> stands) {
        this.stands = stands;
    }
}
