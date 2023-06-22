package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.exception.ErrorTypeCannotBeDeletedException;
import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ErrorType;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ErrorTypeDao {
  private final SessionFactory sessionFactory;

  public ErrorTypeDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
  public void updateErrorType(ErrorType errorType) {
    Session session = sessionFactory.getCurrentSession();
    Optional<ErrorType> findErrorType = getErrorTypeByKey(errorType.getErrorKey(), LockModeType.PESSIMISTIC_WRITE);
    ErrorType errorTypeForUpdate =
        findErrorType.orElseThrow(() -> new IllegalArgumentException("not found error key with id " + errorType.getErrorKey()));
    int version = errorType.getVersion();
    if (errorType.getVersion() != errorTypeForUpdate.getVersion()) {
      throw new ConcurrentModificationException(" entity was modified from another session");
    }
    version++;
    errorTypeForUpdate.setVersion(version);
    errorTypeForUpdate.setComment(errorType.getComment());
    session.update(errorTypeForUpdate);
  }

  public Optional<ErrorType> getErrorTypeByKey(String errorKey) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("select e " +
            "from ErrorType  e where e.errorKey = :errorKey", ErrorType.class)
        .setParameter("errorKey", errorKey)
        .getResultList().stream().findFirst();
  }

  public Optional<ErrorType> getErrorTypeByKey(String errorKey, LockModeType lockModeType) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("select e " +
            "from ErrorType  e where e.errorKey = :errorKey", ErrorType.class)
        .setParameter("errorKey", errorKey)
        .setLockMode(lockModeType)
        .getResultList().stream().findFirst();
  }

  public void deleteErrorType(long errorTypeId) {
    Session session = sessionFactory.getCurrentSession();
    ErrorType errorTypeToRemoved = session.get(ErrorType.class, errorTypeId);
    if (errorTypeToRemoved == null) {
      return;
    }
    session.delete(errorTypeToRemoved);
  }

  public void deleteErrorTypeByKey(String errorKey) {
    Session session = sessionFactory.getCurrentSession();

    Optional<ErrorType> errorType = getErrorTypeByKey(errorKey, LockModeType.PESSIMISTIC_WRITE);
    if (errorType.isEmpty()) {
      return;
    }
    long countError = countErrorByKey(errorKey);

    if (countError > 0) {
      throw new ErrorTypeCannotBeDeletedException("this key cannot be deleted because it is used in error links");
    }

    session.delete(errorType.get());
  }

  public long countErrorByKey(String errorKey) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("""
            select cte from ContractTestError cte
            where  exists (
                select 1
                from ErrorType et where  cte.errorType = et
            and et.errorKey = :errorKey)
            """, ContractTestError.class)
        .setParameter("errorKey", errorKey)
        .stream().count();
  }

  public void saveErrorType(ErrorType errorType) {
    Session session = sessionFactory.getCurrentSession();
    session.save(errorType);
  }

  public List<ErrorType> getAllErrorTypes() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("select e from ErrorType e ORDER BY e.errorKey", ErrorType.class).getResultList();
  }

}
