package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.ErrorType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ErrorTypeDao {

  private final SessionFactory sessionFactory;

  public ErrorTypeDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public ErrorType getErrorType(Long errorTypeId) {
    Session session = sessionFactory.getCurrentSession();
    return session.get(ErrorType.class, errorTypeId);
  }

  public void saveErrorType(ErrorType errorType) {
    Session session = sessionFactory.getCurrentSession();
    session.save(errorType);
  }

  public void updateErrorType(ErrorType errorType) {
    Session session = sessionFactory.getCurrentSession();
    ErrorType errorTypeForUpdate = session.get(ErrorType.class, errorType.getId());
    if (errorTypeForUpdate != null) {
      errorTypeForUpdate.setErrorKey(errorType.getErrorKey());
      errorTypeForUpdate.setComments(errorType.getComments());
      session.save(errorTypeForUpdate);
    }
  }

  public void delete(long errorTypeId) {
    Session session = sessionFactory.getCurrentSession();
    ErrorType errorTypeToRemoved = session.get(ErrorType.class, errorTypeId);
    if (errorTypeToRemoved == null) {
      return;
    }
    session.delete(errorTypeToRemoved);
  }
}
