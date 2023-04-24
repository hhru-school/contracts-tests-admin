package com.hh.contractstestsadmin.exception;


import javax.ws.rs.NotFoundException;

public class ValidationHistoryNotFoundException extends NotFoundException {

    public ValidationHistoryNotFoundException(String s){
        super(s);
    }

}
