package pbotubesmcd.models;

import pbotubesmcd.enums.UserRole;

public class User {
    private Integer id_user;
    private final String username;
    private final String password;
    private final UserRole role;

    public User(Integer id_user, String username, String password, UserRole role) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Integer getIdUser() {
        return this.id_user;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public UserRole getRole() {
        return this.role;
    }
}
