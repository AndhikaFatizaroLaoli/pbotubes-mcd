package pbotubesmcd.controllers;

import pbotubesmcd.models.User;

public class UserController extends GenericsController<User, Integer> {
    public UserController() {
        super(User.class);
    }
}
