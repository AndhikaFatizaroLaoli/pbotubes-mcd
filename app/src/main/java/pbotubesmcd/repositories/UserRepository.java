package pbotubesmcd.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pbotubesmcd.models.User;
import pbotubesmcd.utils.HibernateUtil;

public class UserRepository {

    public static User login(String username, String password) {

        User user;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User WHERE username = :username AND password = :password";
            user = session.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
        }

        return user;
    }

    public static void addUser(User user) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        }
    }

    public static List<User> getAllUser() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User u ORDER BY u.id_user ASC", User.class).list();
        }
    }

    public static void updateUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
        }
    }

    public static void deleteUser(Integer idUser) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, idUser);
            if (user != null) {
                session.remove(user);
            }
            tx.commit();
        }
    }
}
