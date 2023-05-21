package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ServiceDao {
    private final SessionFactory sessionFactory;

    public ServiceDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Service getService(Long serviceId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Service.class, serviceId);
    }

    public void saveService(Service service) {
        Session session = sessionFactory.getCurrentSession();
        session.save(service);
    }

    public void updateService(Service service) {
        Session session = sessionFactory.getCurrentSession();
        Service serviceForUpdate = session.get(Service.class, service.getId());
        if (serviceForUpdate != null) {
            serviceForUpdate.setServiceType(service.getServiceType());
            serviceForUpdate.setServiceName(service.getServiceName());
            serviceForUpdate.setTag(service.getTag());
            serviceForUpdate.setStandName(service.getStandName());
            serviceForUpdate.setExpectationLink(service.getExpectationLink());
            serviceForUpdate.setSchemaLink(service.getSchemaLink());
            session.save(serviceForUpdate);
        }
    }

    public void delete(long serviceId) {
        Session session = sessionFactory.getCurrentSession();
        Service serviceToRemoved = session.get(Service.class, serviceId);
        if (serviceToRemoved == null) {
            return;
        }
        session.delete(serviceToRemoved);
    }
}
