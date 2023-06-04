package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.ContractTestError;
import com.hh.contractstestsadmin.model.ServiceRelation;
import com.hh.contractstestsadmin.model.ErrorType;
import com.hh.contractstestsadmin.model.Expectation;
import java.util.Optional;
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
                        "e.producer, e.consumer, count(e), count(ctr)) FROM Expectation e"+
                        " LEFT JOIN ContractTestError ctr ON ctr.expectation.id = e.id " +
                        "  WHERE e.validation.id = :validationId" +
                        " GROUP BY e.producer, e.consumer", ServiceRelation.class)
                .setParameter("validationId", validationId)
                .getResultList();
    }

    public List<Expectation> getExpectations(Long validationId,
                                             Long consumerId, Long producerId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "select e FROM Expectation e" +
                                " LEFT JOIN FETCH e.contractTestErrors ctr" +
                                " LEFT JOIN FETCH ctr.errorType et" +
                                " WHERE e.validation.id = :validationId" +
                                " AND e.producer.id =:producerId AND e.consumer.id =:consumerId", Expectation.class)
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
        Optional<ErrorType> findErrorType = getErrorTypeByKey(errorType.getErrorKey());
        ErrorType errorTypeForUpdate = findErrorType.orElseThrow(() -> new IllegalArgumentException("error key already exist with id " + errorType.getErrorKey()));
        errorTypeForUpdate.setComments(errorType.getComments());
        session.update(errorTypeForUpdate);
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

        Optional<ErrorType> errorType = getErrorTypeByKey(errorKey);
        if (errorType.isEmpty()) {
            return;
        }
        long countError = countErrorByKey(errorKey);

        if (countError > 0) {
            throw new IllegalArgumentException("this key cannot be deleted because it is used in error links");
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

    public List<ErrorType> getAllErrorTypes() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select e from ErrorType e", ErrorType.class).getResultList();
    }

    public Optional<ErrorType> getErrorTypeByKey(String errorKey) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select e " +
                "from ErrorType  e where e.errorKey = :errorKey", ErrorType.class)
            .setParameter("errorKey", errorKey)
            .getResultList().stream().findFirst();
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
