package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ładowanie sterownika
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", // Użytkownik bazy danych
                    "coderslab" // Hasło do bazy danych
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException("Nie znaleziono sterownika JDBC", e);
        }
    }
}
