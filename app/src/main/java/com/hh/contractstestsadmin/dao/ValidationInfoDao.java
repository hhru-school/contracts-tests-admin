package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.ErrorType;
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
                        "p, c, count(e), count(ctr)) FROM Expectation e" +
                        " JOIN Service p ON p.id = e.producer.id " +
                        " JOIN Service c ON c.id = e.consumer.id" +
                        " JOIN ContractTestError ctr ON ctr.expectation.id = e.id " +
                        " JOIN Validation v ON v.id = e.validation.id" +
                        "  WHERE e.validation.id = :validationId" +
                        " GROUP BY p, c", ServiceRelation.class)
                .setParameter("validationId", validationId)
                .getResultList();
    }

    public List<Expectation> getExpectations(Long validationId,
                                             Long consumerId, Long producerId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "select e FROM Expectation e" +
                                " JOIN Service p ON p.id = e.producer.id" +
                                " JOIN Service c ON c.id = e.consumer.id" +
                                " JOIN ContractTestError ctr ON ctr.expectation.id = e.id" +
                                " JOIN ErrorType et ON et.id = ctr.errorType.id" +
                                " JOIN Validation v ON v.id = e.validation.id" +
                                " WHERE e.validation.id = :validationId" +
                                " AND p.id =:producerId AND c.id =:consumerId", Expectation.class)
                .setParameter("validationId", validationId)
                .setParameter("consumerId", consumerId)
                .setParameter("producerId", producerId)
                .getResultList();
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
            errorType.setComments(errorType.getComments());
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
