package pbotubesmcd.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pbotubesmcd.models.Menu;
import pbotubesmcd.utils.HibernateUtil;

public class MenusRepository {
    public static void addMenu(Menu menu) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(menu);
            tx.commit();
        }
    }

    public static List<Menu> getAllMenus() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM menus", Menu.class).list();
        }
    }

    public static void updateMenu(Menu menu) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(menu);
            tx.commit();
        }
    }

    public static void deleteMenu(Integer idMenu) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Menu menu = session.get(Menu.class, idMenu);
            if (menu != null) {
                session.remove(menu);
            }
            tx.commit();
        }
    }
}
