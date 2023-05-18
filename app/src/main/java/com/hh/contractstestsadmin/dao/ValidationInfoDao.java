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
                        "p, c, count(e.requestPath), count(ctr.id)) FROM Expectation e" +
                        " JOIN FETCH Service p ON p.id = e.producer.id " +
                        " JOIN FETCH Service c ON c.id = e.consumer.id" +
                        " JOIN FETCH ContractTestError ctr ON ctr.expectation.id = e.id " +
                        "   WHERE e.validation.id = :validationId" +
                        " GROUP BY p, c, e.requestPath, ctr.id", ServiceRelation.class)
                .setParameter("validationId", validationId)
                .getResultList();
    }

    public List<Expectation> getExpectations(Long validationId,
                                             Long consumerId, Long producerId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "select e FROM Expectation e" +
                                " JOIN FETCH Service p ON p.id = e.producer.id " +
                                " JOIN FETCH Service c ON c.id = e.consumer.id" +
                                "  WHERE e.validation.id = :validationId", Expectation.class)
                .setParameter("validationId", validationId)
                .getResultList();
    }

    public List<Error> getErrors(Long producerId, Long consumerId, Long expectationId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT co from ContractTestError co" +
                        " LEFT JOIN FETCH ErrorType e ON co.errorType.id = e.id" +
                        " JOIN FETCH Expectation ex ON ex.id = co.expectation.id " +
                        " JOIN FETCH Service p ON p.id = ex.producer.id" +
                        " JOIN FETCH Service c ON c.id = ex.consumer.id" +
                        " WHERE p.id =:producerId AND c.id =:consumerId", Error.class)
                .setParameter("producerId", producerId)
                .setParameter("consumerId", consumerId)
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
