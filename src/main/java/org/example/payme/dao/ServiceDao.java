package org.example.payme.dao;
import jakarta.persistence.EntityManager;
import org.example.payme.entity.Service;
import org.example.payme.util.HibernateUtil;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceDao {
    public void save(Service service) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(service);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public Service findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.find(Service.class, id);
    }

    public List<Service> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM Service", Service.class).getResultList();
    }

    public List<Service> findActiveServices() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM Service s WHERE s.active = true", Service.class).getResultList();
    }

    public void delete(Service service) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(service) ? service : em.merge(service));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
