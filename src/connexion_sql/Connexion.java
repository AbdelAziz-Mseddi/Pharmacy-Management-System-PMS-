package connexion_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/pharmacie?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "mot_de_passe";
    
    private static Connection connection = null;
    
    // Method to establish connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base de données réussie!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL non trouvé!");
            System.err.println("Assurez-vous d'avoir mysql-connector-java dans votre classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données!");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Code d'erreur SQL: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur inattendue lors de la connexion!");
            e.printStackTrace();
        }
        return connection;
    }
    
    // Method to close connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
