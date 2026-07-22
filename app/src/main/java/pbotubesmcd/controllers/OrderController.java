package pbotubesmcd.controllers;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pbotubesmcd.enums.OrderStatus;
import pbotubesmcd.interfaces.GetById;
import pbotubesmcd.models.Order;
import pbotubesmcd.utils.HibernateUtil;

public class OrderController extends GenericsController<Order, Integer> implements GetById<Order> {
    public OrderController() {
        super(Order.class);
    }

    public void updateStatus(Integer idOrder, OrderStatus statusBaru) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "UPDATE Order SET status = :status WHERE id = :id";

            session.createMutationQuery(hql)
                    .setParameter("status", statusBaru)
                    .setParameter("id", idOrder)
                    .executeUpdate();

            tx.commit();
        }
    }

    public Order getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Order.class, id);
        }
    }
}
