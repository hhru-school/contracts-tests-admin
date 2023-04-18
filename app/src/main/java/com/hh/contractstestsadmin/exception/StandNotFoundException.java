package com.hh.contractstestsadmin.exception;

import javax.ws.rs.NotFoundException;

public class StandNotFoundException extends NotFoundException {

    public StandNotFoundException(String s){
        super(s);
    }

}
