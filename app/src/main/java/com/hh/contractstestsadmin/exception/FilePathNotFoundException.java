package com.hh.contractstestsadmin.exception;

import javax.ws.rs.NotFoundException;

public class FilePathNotFoundException extends NotFoundException {

  public FilePathNotFoundException(String s) {
    super(s);
  }

}
