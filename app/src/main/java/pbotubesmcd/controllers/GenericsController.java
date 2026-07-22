package pbotubesmcd.controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pbotubesmcd.utils.HibernateUtil;

public class GenericsController<T, ID> {
    private final Class<T> entityClass;

    public GenericsController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM " + entityClass.getName() + " u ORDER BY u.id ASC";
            return session.createQuery(hql, entityClass).list();
        }
    }

    public void add(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }

    public void update(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public void delete(ID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.remove(entity);
            }
            tx.commit();
        }
    }
}
