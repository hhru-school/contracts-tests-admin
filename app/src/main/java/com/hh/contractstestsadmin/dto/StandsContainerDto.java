package com.hh.contractstestsadmin.dto;

import java.util.List;

public class StandsContainerDto {

    private String releaseId;

    private List<StandDto> stands;

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public List<StandDto> getStands() {
        return stands;
    }

    public void setStands(List<StandDto> stands) {
        this.stands = stands;
    }
}
