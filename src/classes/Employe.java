package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import connexion_sql.Connexion;

public class Employe {
    //TOUSKIE COMMANDE/FOURNISSEUR
    public int ajouterFournisseur(String entreprise, int tel, String mail) throws SQLException {

    String sql = "INSERT INTO Fournisseur (entreprise, tel, mail) VALUES (?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, entreprise);
        ps.setInt(2, tel);
        ps.setString(3, mail);

        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1); // idFournisseur generated
        }
    }

    return -1; // insertion failed
    }

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
    //TOUSKIE VENTE/CLIENT
    public int creerClient(String nom, String prenom, int tel) throws SQLException {
    String sql = "INSERT INTO Clients (nom, prenom, tel) VALUES (?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setInt(3, tel);

        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1); // idClient generated
        }
    }
    return -1; // failed
    }

    public int creerFactureVente(int idClient, LocalDate dateVente, float total) throws SQLException {
    String sql = "INSERT INTO FactureVente (idClient, dateVente, total) VALUES (?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setInt(1, idClient);
        ps.setDate(2, Date.valueOf(dateVente));
        ps.setFloat(3, total);

        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1); // idVente generated
        }
    }
    return -1; // failed
    }

    public boolean creerDetailsVente(
        int idVente,
        int idMedicament,
        float prixUnitaireVente,
        int quantite
    ) throws SQLException {

        String sql = "INSERT INTO DetailsVente (idVente, idMedicament, prixUnitaireVente, quantite) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVente);
            ps.setInt(2, idMedicament);
            ps.setFloat(3, prixUnitaireVente);
            ps.setInt(4, quantite);

            return ps.executeUpdate() > 0; // true if row inserted
        }
    }


    

}
