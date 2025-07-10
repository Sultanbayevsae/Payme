package org.example.payme.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.payme.entity.Notification;
import org.example.payme.util.HibernateUtil;

import java.util.List;

public class NotificationDao {
    public void save(Notification notification) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(notification);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public List<Notification> findByUserId(Long userId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("FROM Notification n WHERE n.user.id = :userId", Notification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void markAsRead(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Notification notif = em.find(Notification.class, id);
            if (notif != null) {
                notif.setRead(true);
                em.merge(notif);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
