package org.example.payme.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.payme.entity.Invoice;
import org.example.payme.util.HibernateUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDao {
    public void save(Invoice invoice) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(invoice);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
    public void update(Invoice invoice) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(invoice);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
    }
    public void delete(Invoice invoice) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(invoice);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
    }
    public Invoice findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.find(Invoice.class, id);
    }
    public List<Invoice> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM Invoice", Invoice.class).getResultList();
    }

    public List<Invoice> findByUserId(Long userId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM Invoice i WHERE i.user.id = :userId", Invoice.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Invoice> findByStatus(String status) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM Invoice i WHERE i.status = :status", Invoice.class)
                .setParameter("status", status)
                .getResultList();
    }
}
