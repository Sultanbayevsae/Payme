package org.example.payme.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.example.payme.entity.Card;
import org.example.payme.util.HibernateUtil;

import java.util.List;

public class CardDao {
    public void save(Card card) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(card);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public Card findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.find(Card.class, id);
    }

    public List<Card> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM Card", Card.class).getResultList();
    }

    public void update(Card card) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(card);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Card card) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Card managed = em.merge(card);
            em.remove(managed);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public List<Card> findByUserId(Long userId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("select c from Card c where c.user.id = :userId",Card.class)
                    .setParameter("userId",userId)
                    .getResultList();
        }finally {
            em.close();
        }
    }

    public Card findByNumber(String cardNumber) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Card c WHERE c.cardNumber = :cardNumber", Card.class)
                    .setParameter("cardNumber", cardNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean existsByCardNumber(String cardNumber) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(c) FROM Card c WHERE c.cardNumber = :cardNumber", Long.class)
                    .setParameter("cardNumber", cardNumber)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }



}
