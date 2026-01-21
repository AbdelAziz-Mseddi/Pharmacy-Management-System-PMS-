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
    private static final int SEUIL_STOCK = 50;
    private int idEmploye;
    private String nom;
    private String prenom;
    private int tel;
    private String mail;
    private String mdp;
    private String privilege;
    public Employe() {
    }

    public Employe(int idEmploye, String nom, String prenom, int tel, String mail, String mdp, String privilege) {
        this.idEmploye = idEmploye;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.mdp = mdp;
        this.privilege = privilege;
    }

    public int getIdEmploye() { return idEmploye; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public int getTel() { return tel; }
    public String getMail() { return mail; }
    public String getMdp() { return mdp; }
    public String getPrivilege() { return privilege; }
    
    //TOUSKIE COMMANDE/FOURNISSEUR
    public int ajouterFournisseur(String entreprise, int tel, String mail) throws SQLException {

    String sql = "INSERT INTO Fournisseur (entreprise, tel, mail) VALUES (?, ?, ?)";

    try (Connection conn = Connexion.getConnection();
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
	        LocalDate dateLivraisonPrevue,
	        int total
	) throws SQLException {

	    String sql =
	        "UPDATE Commande " +
	        "SET dateLivraisonPrevue = ?, total = ? " +
	        "WHERE idCommande = ? AND etat = 'en cours'";
	    Connection conn = Connexion.getConnection();

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setDate(1, Date.valueOf(dateLivraisonPrevue));
	        ps.setInt(2, total);
	        ps.setInt(3, idCommande);

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

        try (Connection conn = Connexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idFournisseur);
            ps.setDate(2, java.sql.Date.valueOf(dateCommande));
            ps.setDate(3, java.sql.Date.valueOf(dateLivraisonPrevue));

            // ✅ Gestion du null
            if (dateLivraisonReelle != null) {
                ps.setDate(4, java.sql.Date.valueOf(dateLivraisonReelle));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setInt(5, total);

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1); // idCommande généré
            }
        }

        return -1;
    }

    public boolean ajouterDetailsCommande(
        int idCommande,
        int idMedicament,
        int quantite,
        float prixUnitaireAchat
    ) throws SQLException {

        String sql =
            "INSERT INTO DetailsCommande " +
            "(idCommande, idMedicament, quantite, prixUnitaireAchat) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCommande);
            ps.setInt(2, idMedicament);
            ps.setInt(3, quantite);
            ps.setFloat(4, prixUnitaireAchat);

            return ps.executeUpdate() == 1;
        }
    }

    //TOUSKIE VENTE/CLIENT
    public static int creerClient(String nom, String prenom, int tel) throws SQLException {
    String sql = "INSERT INTO Clients (nom, prenom, tel) VALUES (?, ?, ?)";

    try (Connection conn = Connexion.getConnection();
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

    public static int creerFactureVente(int idClient, LocalDate dateVente, float total) throws SQLException {
    String sql = "INSERT INTO FactureVente (idClient, dateVente, total) VALUES (?, ?, ?)";

    try (Connection conn = Connexion.getConnection();
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

    public static boolean  creerDetailsVente(
        int idVente,
        int idMedicament,
        float prixUnitaireVente,
        int quantite
    ) throws SQLException, StockInsuffisantException {

        Connection conn = Connexion.getConnection();
        
        // Check current stock
        String checkStockSql = "SELECT quantite FROM Medicament WHERE idMedicament = ?";
        try (PreparedStatement psCheck = conn.prepareStatement(checkStockSql)) {
            psCheck.setInt(1, idMedicament);
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) {
                int stockActuel = rs.getInt("quantite");
                
                // Check if stock is sufficient
                if (stockActuel < quantite) {
                    throw new StockInsuffisantException(
                        "Stock insuffisant pour le médicament ID " + idMedicament + 
                        ". Stock actuel: " + stockActuel + ", Quantité demandée: " + quantite
                    );
                }
                
                // Check if stock after sale would fall below threshold
                if ((stockActuel - quantite) < SEUIL_STOCK) {
                    throw new StockInsuffisantException(
                        "Stock insuffisant pour le médicament ID " + idMedicament + 
                        ". Le stock après vente (" + (stockActuel - quantite) + 
                        ") serait inférieur au seuil (" + SEUIL_STOCK+")"
					);
                }
            } else {
                throw new SQLException("Médicament introuvable avec l'ID: " + idMedicament);
            }
        }
        
        // Insert sale details
        String sql = "INSERT INTO DetailsVente (idVente, idMedicament, prixUnitaireVente, quantite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVente);
            ps.setInt(2, idMedicament);
            ps.setFloat(3, prixUnitaireVente);
            ps.setInt(4, quantite);

            int rowsInserted = ps.executeUpdate();
            
            // Update stock if insertion successful
            if (rowsInserted > 0) {
                String updateStockSql = "UPDATE Medicament SET quantite = quantite - ? WHERE idMedicament = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(updateStockSql)) {
                    psUpdate.setInt(1, quantite);
                    psUpdate.setInt(2, idMedicament);
                    psUpdate.executeUpdate();
                }
                return true;
            }
            return false;
        }
    }
