package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.Validation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class ValidationDao {
  private final SessionFactory sessionFactory;

  @Inject
  public ValidationDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void save(Validation validation) {
    Session session = sessionFactory.getCurrentSession();
    session.save(validation);
  }

  public void update(Validation validation) {
    Session session = sessionFactory.getCurrentSession();
    Validation validationToUpdated = session.get(Validation.class, validation.getId());
    if (validationToUpdated != null) {
      validationToUpdated.setCreatedDate(validation.getCreatedDate());
      validationToUpdated.setErrorCount(validation.getErrorCount());
      validationToUpdated.setStatus(validation.getStatus());
      session.save(validationToUpdated);
    }
  }

  public Optional<Validation> getValidationById(UUID validationId) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Validation.class, validationId));
  }

  public List<Validation> getAllValidations() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("select vh from Validation vh", Validation.class).getResultList();
  }

  public void delete(UUID validationId) {
    Session session = sessionFactory.getCurrentSession();
    Validation validationToRemoved = session.get(Validation.class, validationId);
    if (validationToRemoved == null) {
      return;
    }
    session.delete(validationToRemoved);
  }

}
