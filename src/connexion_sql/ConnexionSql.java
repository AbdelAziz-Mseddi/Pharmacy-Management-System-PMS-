package connexion_sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionSql {
    private static final String URL =
            "jdbc:mysql://localhost:3306/pharmacie?useSSL=false&serverTimezone=UTC";

    private static final String USER = "projetTP";
    private static final String PASSWORD = "POO&BD2026";



    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
