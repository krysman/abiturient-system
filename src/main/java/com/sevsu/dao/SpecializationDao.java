package com.sevsu.dao;

import com.sevsu.model.Specialization;
import com.sevsu.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class SpecializationDao extends AbstractDao {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public void saveSpecialization(Specialization specialization) {
        save(specialization);
    }

    public void updateSpecialization(Specialization specialization) {
        update(specialization);
    }

    public void deleteSpecialization(Specialization specialization) {
        delete(specialization);
    }

    public Specialization getSpecializationByDepartment(String department) {
        Session session = getSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Specialization.class);
        criteria.add(Restrictions.eq("department", department));
        Specialization specialization = (Specialization) criteria.uniqueResult();
        session.getTransaction().commit();
        return specialization;
    }

    public List<Specialization> getAllSpecializations() {
        Session session = getSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Specialization.class);
        List<Specialization> specializations = (List<Specialization>) criteria.list();
        session.getTransaction().commit();
        return specializations;
    }
}
