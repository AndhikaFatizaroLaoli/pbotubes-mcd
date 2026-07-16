package pbotubesmcd.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pbotubesmcd.models.Categories;
import pbotubesmcd.utils.HibernateUtil;

public class CategoriesRepository {
    public static void addCategory(Categories category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(category);
            tx.commit();
        }
    }

    public static List<Categories> getAllCategory() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM categories", Categories.class).list();
        }
    }

    public static void updateCategory(Categories category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(category);
            tx.commit();
        }
    }

    public static void deleteCategory(Integer idCategory) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Categories category = session.get(Categories.class, idCategory);
            if (category != null) {
                session.remove(category);
            }
            tx.commit();
        }
    }
}
