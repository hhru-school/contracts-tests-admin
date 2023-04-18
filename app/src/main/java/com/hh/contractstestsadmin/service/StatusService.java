package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ServiceDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StatusService {

    public StandsContainerDto getStandsInfo(){
        return new StandsContainerDto();
    }

    public List<ServiceDto> getServicesInfo(String standName) throws StandNotFoundException {
        return new ArrayList<>();
    }

}
