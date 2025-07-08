package org.example.payme.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.payme.entity.Transaction;
import org.example.payme.util.HibernateUtil;

import java.util.List;

public class TransactionDao {

    public void save(Transaction txObj) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(txObj);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public Transaction findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.find(Transaction.class, id);
    }

    public List<Transaction> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM Transaction", Transaction.class).getResultList();
    }

    public void update(Transaction txObj) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(txObj);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Transaction txObj) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Transaction managed = em.merge(txObj);
            em.remove(managed);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public List<Transaction> findByUserId(Long userId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT t FROM Transaction t WHERE t.user.id = :userId", Transaction.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Transaction> findByCardId(Long cardId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT t FROM Transaction t WHERE t.card.id = :cardId", Transaction.class)
                    .setParameter("cardId", cardId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Transaction> findByStatus(String status) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT t FROM Transaction t WHERE t.status = :status", Transaction.class)
                    .setParameter("status", status)
                    .getResultList();
        } finally {
            em.close();
        }
    }



}
