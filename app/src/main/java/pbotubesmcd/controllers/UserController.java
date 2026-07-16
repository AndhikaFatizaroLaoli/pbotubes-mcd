package pbotubesmcd.controllers;

import java.util.List;

import pbotubesmcd.models.User;
import pbotubesmcd.repositories.UserRepository;

public class UserController {
    public List<User> getAllUser() {
        return UserRepository.getAllUser();
    }

    public void addUser(User user) {
        UserRepository.addUser(user);
    }

    public void updateUser(User user) {
        UserRepository.updateUser(user);
    }

    public void deleteUser(Integer idUser) {
        UserRepository.deleteUser(idUser);
    }
}
