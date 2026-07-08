package pbotubesmcd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pbotubesmcd.AppConfig;

public class Database {
    public static final String URL = "jdbc:postgresql://" + AppConfig.HOST + ":" + AppConfig.PORT + "/"
            + AppConfig.DATABASE;

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, AppConfig.USER, AppConfig.PASSWORD);
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        try (Connection conn = Database.connect()) {
            if (conn != null) {
                System.out.println("Connection successful using Database.connect()!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (Exception e) {
            System.out.println("❌ Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
