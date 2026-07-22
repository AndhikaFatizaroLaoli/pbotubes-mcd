package pbotubesmcd.repositories;

import org.hibernate.Session;

import pbotubesmcd.models.User;
import pbotubesmcd.utils.HibernateUtil;

public class AuthRepository {

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

}
