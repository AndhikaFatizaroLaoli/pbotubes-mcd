package pbotubesmcd.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pbotubesmcd.models.OrderDetail;
import pbotubesmcd.utils.HibernateUtil;

public class OrderDetails {
    public static void addOrderDetails(OrderDetail orderDetail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(orderDetail);
            tx.commit();
        }
    }

    public static List<OrderDetail> getAllOrderDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM order_details", OrderDetail.class).list();
        }
    }

    public static void updateOrderDetails(OrderDetail orderDetail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(orderDetail);
            tx.commit();
        }
    }

    public static void deleteOrderDetails(Integer idOrderDetail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            OrderDetail orderDetail = session.get(OrderDetail.class, idOrderDetail);
            if (orderDetail != null) {
                session.remove(orderDetail);
            }
            tx.commit();
        }
    }
}
