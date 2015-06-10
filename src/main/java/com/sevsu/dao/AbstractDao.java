package com.sevsu.dao;

import com.sevsu.util.HibernateUtil;
import org.hibernate.Session;

public class AbstractDao {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public void save(Object entity) {
        Session session = getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    public void update(Object entity) {
        Session session = getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    public void delete(Object entity) {
        Session session = getSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
    }
}
