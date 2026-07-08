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
            } else {
                // navigasi ke HomeCustomerView.java
            }

            return true;
        } else {
            return false;
            // nanti di LoginView nya tampilin pesan error mau text ataupun pop up bebas,
            // tampilin username atau password salah
        }
    }
}
