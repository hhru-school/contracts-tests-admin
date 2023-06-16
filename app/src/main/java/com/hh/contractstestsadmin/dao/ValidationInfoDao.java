package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.Expectation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ValidationInfoDao {
  private final SessionFactory sessionFactory;

  public ValidationInfoDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<ServiceRelation> getServiceRelations(Long validationId) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("SELECT new com.hh.contractstestsadmin.model.ServiceRelation(" +
            "e.producer, e.consumer, count(e), count(ctr)) FROM Expectation e" +
            " LEFT JOIN ContractTestError ctr ON ctr.expectation.id = e.id " +
            "  WHERE e.validation.id = :validationId" +
            " GROUP BY e.producer, e.consumer", ServiceRelation.class)
        .setParameter("validationId", validationId)
        .getResultList();
  }

  public List<Expectation> getExpectations(
      String standName, Long validationId,
      Long consumerId, Long producerId
  ) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery(
            "select e FROM Expectation e " +
                " LEFT JOIN FETCH e.contractTestErrors ctr " +
                " LEFT JOIN FETCH ctr.errorType et " +
                " WHERE e.validation.id = :validationId " +
                " AND e.producer.id =:producerId AND e.validation.standName = :standName " +
                " AND e.consumer.id =:consumerId", Expectation.class)
        .setParameter("validationId", validationId)
        .setParameter("consumerId", consumerId)
        .setParameter("producerId", producerId)
        .setParameter("standName", standName)
        .getResultList();
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
