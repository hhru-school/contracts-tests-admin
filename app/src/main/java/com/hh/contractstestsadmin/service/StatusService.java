package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ServiceDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StatusService {

    public StandsContainerDto getStands(){
        return new StandsContainerDto();
    }

    public List<ServiceDto> getServices(String standName) throws StandNotFoundException {
        return new ArrayList<>();
    }

}
