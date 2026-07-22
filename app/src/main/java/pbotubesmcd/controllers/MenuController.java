package pbotubesmcd.controllers;

import java.util.List;

import org.hibernate.Session;

import pbotubesmcd.models.Menu;
import pbotubesmcd.utils.HibernateUtil;

public class MenuController extends GenericsController<Menu, Integer> {
    public MenuController() {
        super(Menu.class);
    }

    public List<Menu> getAllByCatId(Integer catId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Menu u WHERE u.category.id = :id ORDER BY u.id ASC";
            return session.createQuery(hql, Menu.class).setParameter("id", catId).list();
        }
    }
}
