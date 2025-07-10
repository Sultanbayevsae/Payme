package org.example.payme.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.example.payme.entity.User;
import org.example.payme.util.HibernateUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    public void save(User user) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public User findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM User", User.class).getResultList();
    }

    public void update(User user) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(User user) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User managedUser = em.merge(user);
            em.remove(managedUser);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public User findByPhone(String phone) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT user FROM User user WHERE user.phoneNumber = :phone", User.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean existsByPhone(String phone) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(u) FROM User u WHERE u.phoneNumber = :phone", Long.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
