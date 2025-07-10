package org.example.payme.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.example.payme.entity.AuthToken;
import org.example.payme.util.HibernateUtil;
import org.springframework.stereotype.Repository;

@Repository
public class AuthTokenDao {
    public void save(AuthToken token) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(token);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public AuthToken findByToken(String tokenValue) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("FROM AuthToken a WHERE a.token = :token", AuthToken.class)
                    .setParameter("token", tokenValue)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean existsByToken(String tokenValue) {
        return findByToken(tokenValue) != null;
    }

    public void delete(AuthToken token) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(token) ? token : em.merge(token));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
