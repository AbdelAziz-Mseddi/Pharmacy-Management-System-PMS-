package controle_interface;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import connexion_sql.Connexion;

public class commandeDAO {
	public boolean modifierCommande(
	        int idCommande,
	        int idFournisseur,
	        LocalDate dateLivraisonPrevue,
	        int total
	) throws SQLException {

	    String sql =
	        "UPDATE Commande " +
	        "SET idFournisseur = ?, dateLivraisonPrevue = ?, total = ? " +
	        "WHERE idCommande = ? AND etat = 'en cours'";
	    Connection conn = Connexion.getConnection();

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idFournisseur);
	        ps.setDate(2, Date.valueOf(dateLivraisonPrevue));
	        ps.setInt(3, total);
	        ps.setInt(4, idCommande);

	        return ps.executeUpdate() > 0;
	    }
	}
	
	public boolean annulerCommande(int idCommande) throws SQLException {

	    String sql =
	        "UPDATE Commande SET etat = 'annulee' " +
	        "WHERE idCommande = ? AND etat = 'en cours'";
	    Connection conn = Connexion.getConnection();

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idCommande);
	        return ps.executeUpdate() > 0;
	    }
	}

	public boolean recevoirCommande(
	        int idCommande,
	        LocalDate dateLivraisonReelle
	) throws SQLException {

	    String sql =
	        "UPDATE Commande " +
	        "SET etat = 'livree', dateLivraisonReelle = ? " +
	        "WHERE idCommande = ? AND etat = 'en cours'";
	    Connection conn = Connexion.getConnection();

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setDate(1, Date.valueOf(dateLivraisonReelle));
	        ps.setInt(2, idCommande);

	        return ps.executeUpdate() > 0;
	    }
	}

	public int creerCommande(
	        int idFournisseur,
	        LocalDate dateCommande,
	        LocalDate dateLivraisonPrevue,
	        LocalDate dateLivraisonReelle,
	        int total
	) throws SQLException {

	    String sql =
	        "INSERT INTO Commande " +
	        "(idFournisseur, dateCommande, dateLivraisonPrevue, dateLivraisonReelle, total, etat) " +
	        "VALUES (?, ?, ?, ?, ?, 'en cours')";
	    Connection conn = Connexion.getConnection();

	    try (PreparedStatement ps =
	             conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setInt(1, idFournisseur);
	        ps.setDate(2, Date.valueOf(dateCommande));
	        ps.setDate(3, Date.valueOf(dateLivraisonPrevue));
	        ps.setDate(4, Date.valueOf(dateLivraisonReelle));
	        ps.setInt(5, total);

	        ps.executeUpdate();

	        ResultSet keys = ps.getGeneratedKeys();
	        if (keys.next()) {
	            return keys.getInt(1); // idCommande generated
	        }
	    }
	    return -1;
	}

	
	
}
