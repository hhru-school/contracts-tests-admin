package com.hh.contractstestsadmin.dto;

import java.util.List;

public class StandsContainerDto {

    private Long releaseId;

    private List<StandDto> stands;

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

    public List<StandDto> getStands() {
        return stands;
    }

    public void setStands(List<StandDto> stands) {
        this.stands = stands;
    }
}
