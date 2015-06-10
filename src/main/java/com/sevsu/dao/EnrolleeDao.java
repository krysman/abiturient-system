package com.sevsu.dao;

import com.sevsu.model.Enrollee;
import com.sevsu.util.HibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class EnrolleeDao extends AbstractDao {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public void saveEnrollee(Enrollee enrollee) {
        save(enrollee);
    }

    public void updateEnrollee(Enrollee enrollee) {
        update(enrollee);
    }

    public void deleteEnrollee(Enrollee enrollee) {
        delete(enrollee);
    }

    public Enrollee getEnrolleeById(int enrolleeId) {
        Session session = getSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Enrollee.class);
        criteria.add(Restrictions.eq("id", enrolleeId));
        Enrollee enrollee = (Enrollee) criteria.uniqueResult();
        session.getTransaction().commit();
        return enrollee;
    }

    public List<Enrollee> getAllEnrollee() {
        Session session = getSession();
        session.beginTransaction();
       //Criteria criteria = session.createCriteria(Enrollee.class);
//        List<Enrollee> enrollees = session.createQuery("from ENROLLEES").list();
        //List<Enrollee> enrollees = (List<Enrollee>) criteria.list();

        List<Enrollee> enrollees = session.createCriteria(Enrollee.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        session.getTransaction().commit();
        return enrollees;
    }



}
