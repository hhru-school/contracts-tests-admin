package com.hh.contractstestsadmin.dao;

import com.hh.contractstestsadmin.model.Service;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ServiceDao {
    private final SessionFactory sessionFactory;

    public ServiceDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Service> getService(Long serviceId) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Service.class, serviceId));
    }

    public Optional<Service> findServiceByFields(String serviceName, String standName, String tag){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                "SELECT service FROM Service service" +
                    " WHERE service.serviceName = :serviceName AND service.standName = :standName AND service.tag = :tag",
                Service.class)
            .setParameter("serviceName", serviceName)
            .setParameter("standName", standName)
            .setParameter("tag", tag)
            .getResultList()
            .stream()
            .findAny();
    }

    public Optional<Service> findServiceByFields(String serviceName, String standName, String tag, LockModeType lockModeType){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                "SELECT service FROM Service service" +
                    " WHERE service.serviceName = :serviceName AND service.standName = :standName AND service.tag = :tag",
                Service.class)
            .setParameter("serviceName", serviceName)
            .setParameter("standName", standName)
            .setParameter("tag", tag)
            .setLockMode(lockModeType)
            .getResultList()
            .stream()
            .findAny();
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
