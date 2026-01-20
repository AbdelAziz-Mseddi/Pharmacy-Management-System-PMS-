package app;

import java.sql.Connection;
import connexion_sql.Connexion;

public class Main {
	public static void main(String[] args) {
		// Test database connection
		Connection conn = Connexion.getConnection();
		
		if (conn != null) {
			System.out.println("La connexion à MySQL a réussi!");
			
			
			
		} else {
			System.out.println("Échec de la connexion à MySQL.");
		}
	}
}
