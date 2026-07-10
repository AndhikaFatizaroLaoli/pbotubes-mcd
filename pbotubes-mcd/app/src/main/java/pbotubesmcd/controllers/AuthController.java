package pbotubesmcd.controllers;

import pbotubesmcd.enums.UserRole;
import pbotubesmcd.models.User;
import pbotubesmcd.repositories.UserRepository;
import pbotubesmcd.utils.Session;

public class AuthController {
    public boolean login(String username, String password) {
        User user = UserRepository.login(username, password);

if (user != null) {
            Session.setCurrentUser(user);

            if (user.getRole().equals(UserRole.ADMIN)) {
                // navigasi ke HomeAdminView.java
                // new pbotubesmcd.views.HomeAdminView().setVisible(true);
            } else {
                // navigasi ke HomeCustomerView.java
                new pbotubesmcd.views.HomeCustomerView().setVisible(true); // <--- Instansiasi saat login sukses
            }

            return true;
        } else {
            return false;
        }
    }
}
