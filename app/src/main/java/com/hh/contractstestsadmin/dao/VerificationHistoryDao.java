package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.VerificationHistory;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class VerificationHistoryDao {
  private final SessionFactory sessionFactory;

  @Inject
  public VerificationHistoryDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void save(VerificationHistory verificationHistory) {
    Session session = sessionFactory.getCurrentSession();
    session.save(verificationHistory);
  }

  public void update(VerificationHistory verificationHistory) {
    Session session = sessionFactory.getCurrentSession();
    VerificationHistory verificationHistoryToUpdated = session.get(VerificationHistory.class, verificationHistory.getId());
    if (verificationHistoryToUpdated != null) {
      verificationHistoryToUpdated.setCreatedDate(verificationHistory.getCreatedDate());
      verificationHistoryToUpdated.setErrorCount(verificationHistory.getErrorCount());
      verificationHistoryToUpdated.setStatus(verificationHistory.getStatus());
      session.save(verificationHistoryToUpdated);
    }
  }

  public VerificationHistory getVerificationHistoryById(UUID verificationHistoryId) {
    Session session = sessionFactory.getCurrentSession();
    return session.get(VerificationHistory.class, verificationHistoryId);
  }

  public List<VerificationHistory> getAllVerificationHistories() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("select vh from VerificationHistory vh", VerificationHistory.class).getResultList();
  }

  public void delete(UUID verificationHistoryId) {
    Session session = sessionFactory.getCurrentSession();
    VerificationHistory verificationHistoryToRemoved = session.get(VerificationHistory.class, verificationHistoryId);
    if (verificationHistoryToRemoved == null) {
      return;
    }
    session.delete(verificationHistoryToRemoved);
  }

}
