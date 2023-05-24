package com.hh.contractstestsadmin.service.util;

import com.hh.contractstestsadmin.dao.ErrorTypeDao;
import com.hh.contractstestsadmin.model.ErrorType;

public class ErrorTypesContextManager {

  private final ErrorTypeDao errorTypeDao;

  public ErrorTypesContextManager(ErrorTypeDao errorTypeDao) {
    this.errorTypeDao = errorTypeDao;
  }

  public ErrorType getOrCreateErrorType(String errorKey){
    return null;
  }

}
