import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/time_management?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";

    // Replace this with your MySQL root password.
    private static final String PASSWORD = "KakaJr.09";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
