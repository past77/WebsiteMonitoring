package connectionToDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    private final String DATA_BASE_URL = "jdbc:postgresql://localhost:5432/monitor";
    private final String USER = "postgres";
    private final String PASSWORD = "123";
    private static Connection connection;
    private static JDBCConnection JDBCConnection;

    private JDBCConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static synchronized Connection getConnection(){
        return connection;
    }

    public static synchronized JDBCConnection getInstance(){
        if (JDBCConnection == null){
            JDBCConnection = new JDBCConnection();
        }
        return JDBCConnection;
    }
}
