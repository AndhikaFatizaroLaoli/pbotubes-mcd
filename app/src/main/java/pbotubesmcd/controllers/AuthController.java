package pbotubesmcd.controllers;

import pbotubesmcd.exceptions.InvalidCredentialsException;
import pbotubesmcd.models.User;
import pbotubesmcd.repositories.UserRepository;
import pbotubesmcd.utils.Session;

public class AuthController {
    public void login(String username, String password) throws InvalidCredentialsException, IllegalArgumentException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username tidak boleh kosong!");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong!");
        }

        User user = UserRepository.login(username, password);

        if (user == null) {
            throw new InvalidCredentialsException("Username atau password salah!");
        }

        Session.setCurrentUser(user);
    }

    public void logout() throws IllegalStateException {
        if (Session.getCurrentUser() == null) {
            throw new IllegalStateException("Session tidak aktif!");
        }

        Session.logout();
    }
}
