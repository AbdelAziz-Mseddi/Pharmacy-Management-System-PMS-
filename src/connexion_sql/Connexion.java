package connexion_sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion 
{
    private static final String URL = "jdbc:mysql://localhost:3306/DATABASE_NAME?useSSL=false&serverTimezone=UTC";
    private static final String USER = "YOUR_USERNAME";
    private static final String PASSWORD = "YOUR_PASSWORD";

    public static Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}


