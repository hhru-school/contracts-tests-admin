package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ErrorProducerConsumerLink;
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

    public List<ErrorProducerConsumerLink> getErrorProducerConsumerLink(Long validationId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT new com.hh.contractstestsadmin.model.ErrorProducerConsumerLink(" +
                        "p.serviceName, co.serviceName) FROM Expectation e" +
                        " LEFT JOIN Service co ON e.consumer.id = co.id" +
                        " LEFT JOIN Service p ON e.producer.id = p.id" +
                        "  WHERE e.validation.id = :validationId", ErrorProducerConsumerLink.class)
                .setParameter("validationId", validationId)
                .getResultList();
    }

    public List<Expectation> getExpectationByValidationIdAndByProducerIdAndConsumerId(Long validationId,
                                                                                      Long consumerId, Long producerId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT e from Expectation  e " +
                        " LEFT JOIN FETCH Service p ON p.id = e.producer.id" +
                        " LEFT JOIN FETCH Service c ON c.id = e.consumer.id" +
                " where e.validation.id = :validationId AND e.producer.id = :producerId AND" +
                        " e.consumer.id =:consumerId", Expectation.class)
                .setParameter("validationId", validationId)
                .setParameter("producerId", producerId)
                .setParameter("consumerId", consumerId)
                .getResultList();
    }

    public List<Error> getErrorByExpectationId(Long expectationId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT co from ContractTestError co" +
                " LEFT JOIN FETCH ErrorType e On co.errorType.id = e.id" +
                " WHERE co.expectation.id =: expectationId", Error.class)
                .setParameter("expectationId", expectationId)
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
