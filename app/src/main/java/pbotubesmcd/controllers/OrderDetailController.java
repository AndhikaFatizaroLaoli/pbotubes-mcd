package pbotubesmcd.controllers;

import java.util.List;

import org.hibernate.Session;

import pbotubesmcd.interfaces.GetAllById;
import pbotubesmcd.models.OrderDetail;
import pbotubesmcd.utils.HibernateUtil;

public class OrderDetailController extends GenericsController<OrderDetail, Integer> implements GetAllById<OrderDetail> {
    public OrderDetailController() {
        super(OrderDetail.class);
    }

    @Override
    public List<OrderDetail> getAllById(int orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM OrderDetail od WHERE od.order.id = :orderId";

            return session.createQuery(hql, OrderDetail.class)
                    .setParameter("orderId", orderId)
                    .list();
        }
    }
}
