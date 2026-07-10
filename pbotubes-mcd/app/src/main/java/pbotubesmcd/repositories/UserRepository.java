package pbotubesmcd.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pbotubesmcd.database.Database;
import pbotubesmcd.enums.UserRole;
import pbotubesmcd.models.User;

public class UserRepository {
    private static final Connection conn;

    static {
        conn = Database.connect();
    }

    private static User mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id_user"), rs.getString("username"), rs.getString("password"),
                UserRole.valueOf(rs.getString("role")));
    }

    public static User login(String username, String password) {
        User user = null;

        String sql = """
                SELECT * FROM "users" WHERE username = ? AND password = ?;
                """;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void addUser(User user) {
        String sql = """
                INSERT INTO "users"(username, password, role) VALUES (?, ?, ?::user_role);
                """;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            // stmt.setInt(1, user.getIdUser());
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllUser() {
        List<User> result = new ArrayList<>();

        String sql = """
                SELECT * FROM "users";
                """;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void updateUser(User user) {
        String sql = """
                UPDATE "users" SET "username" = ?, "password" = ?, "role" = ?::user_role WHERE "id_user" = ?;
                """;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            stmt.setInt(4, user.getIdUser());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(Integer idUser) {
        String sql = """
                DELETE FROM "users" WHERE "id_user" = ?;
                """;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
