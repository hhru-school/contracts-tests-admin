package com.hh.contractstestsadmin.exception;

public class StandsDaoException extends Exception {

  public StandsDaoException(Throwable cause) {
    super(cause);
  }

  public StandsDaoException(String message) {
    super(message);
  }

  public StandsDaoException(String message, Throwable cause) {
    super(message, cause);
  }
}
