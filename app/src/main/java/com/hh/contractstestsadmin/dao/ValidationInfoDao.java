package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ErrorLevel;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.ErrorType;
import com.hh.contractstestsadmin.model.Expectation;
import com.hh.contractstestsadmin.model.Validation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ValidationInfoDao {
  private final SessionFactory sessionFactory;

  public ValidationInfoDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<ServiceRelation> getServiceRelations(Long validationId, ErrorLevel errorLevel) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("SELECT new com.hh.contractstestsadmin.model.ServiceRelation(" +
                    "e.producer, e.consumer, count(distinct e), " +
                    "count(distinct ctr)) FROM Expectation e" +
                    " LEFT JOIN e.contractTestErrors ctr" +
                    " WHERE e.validation.id = :validationId AND ctr.level =: errorLevel" +
                    " GROUP BY e.producer, e.consumer ", ServiceRelation.class)
            .setParameter("validationId", validationId)
            .setParameter("errorLevel", errorLevel)
            .getResultList();
  }

  public List<Expectation> getExpectations(
          String standName, Long validationId,
          Long consumerId, Long producerId, ErrorLevel errorLevel
  ) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery(
                    "select distinct e FROM Expectation e " +
                            " LEFT JOIN FETCH e.contractTestErrors ctr " +
                            " LEFT JOIN FETCH ctr.errorType et " +
                            " WHERE e.validation.id = :validationId " +
                            " AND e.producer.id =:producerId AND e.validation.standName = :standName " +
                            " AND e.consumer.id =:consumerId AND ctr.level =: errorLevel", Expectation.class)
            .setParameter("validationId", validationId)
            .setParameter("consumerId", consumerId)
            .setParameter("producerId", producerId)
            .setParameter("standName", standName)
            .setParameter("errorLevel", errorLevel)
            .getResultList();
  }

  public void updateValidationInfo(Validation validation) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(validation);
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
      errorType.setComment(errorType.getComment());
      session.update(errorType);
    }
  }

  public void deleteErrorType(long errorTypeId) {
    Session session = sessionFactory.getCurrentSession();
    ErrorType errorTypeToRemoved = session.get(ErrorType.class, errorTypeId);
    if (errorTypeToRemoved == null) {
      return;
    }
    session.delete(errorTypeToRemoved);
  }

  public void saveContractTestError(ContractTestError error) {
    Session session = sessionFactory.getCurrentSession();
    session.save(error);
  }

  public void saveExpectation(Expectation expectation) {
    Session session = sessionFactory.getCurrentSession();
    session.save(expectation);
  }

  public void deleteExpectation(long expectationId) {
    Session session = sessionFactory.getCurrentSession();
    Expectation expectationToRemoved = session.get(Expectation.class, expectationId);
    if (expectationToRemoved == null) {
      return;
    }
    session.delete(expectationToRemoved);
  }

}
