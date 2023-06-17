package com.hh.contractstestsadmin.exception;

import javax.ws.rs.NotFoundException;

public class ServiceNotFoundException extends NotFoundException {

  public ServiceNotFoundException(String s) {
    super(s);
  }

}
