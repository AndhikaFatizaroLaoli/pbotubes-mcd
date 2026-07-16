package pbotubesmcd.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pbotubesmcd.models.Order;
import pbotubesmcd.utils.HibernateUtil;

public class OrdersRepository {
    public static void addOrder(Order order) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(order);
            tx.commit();
        }
    }

    public static List<Order> getAllOrders() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM orders", Order.class).list();
        }
    }

    public static void updateOrder(Order order) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(order);
            tx.commit();
        }
    }

    public static void deleteOrder(Integer idOrder) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Order order = session.get(Order.class, idOrder);
            if (order != null) {
                session.remove(order);
            }
            tx.commit();
        }
    }
}