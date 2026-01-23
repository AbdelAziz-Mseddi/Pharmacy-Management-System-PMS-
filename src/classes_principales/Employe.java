package classes_principales;

import interface_fournisseur_dao.MedicamentInterface;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import exceptions_personnalisees.StockInsuffisantException;
import connexion_sql.Connexion;

public class Employe 
{
	
    private int idEmploye;
    private String nom;
    private String prenom;
    private int tel;
    private String mail;
    private String mdp;
    private String privilege;
    
    public Employe() 
    {
    	
    }

    public Employe(int idEmploye, String nom, String prenom, int tel, String mail, String mdp, String privilege) 
    {
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
    
    //FOURNISSEUR/COMMANDE
    static public int ajouterFournisseur(String entreprise, int tel, String mail) throws SQLException 
    {

    	String sql = "INSERT INTO Fournisseur (entreprise, tel, mail) VALUES (?, ?, ?)";

    	try (Connection conn = Connexion.getConnection();
    			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
    	{

	        ps.setString(1, entreprise);
	        ps.setInt(2, tel);
	        ps.setString(3, mail);
	
	        ps.executeUpdate();
	
	        ResultSet keys = ps.getGeneratedKeys();
	        if (keys.next()) 
	        {
	            return keys.getInt(1); //idFournisseur generated
	        }
    }

    return -1; //insertion failed
    
    }

    static public boolean modifierCommande(int idCommande,LocalDate dateLivraisonPrevue) throws SQLException 
    {

	    String sql =
	        "UPDATE Commande " +
	        "SET dateLivraisonPrevue = ?" +
	        "WHERE idCommande = ? AND etat = 'en cours'";
	    
	    Connection conn = Connexion.getConnection();

	    try (PreparedStatement ps = conn.prepareStatement(sql)) 
	    {
	        ps.setDate(1, Date.valueOf(dateLivraisonPrevue));
	        ps.setInt(2, idCommande);

	        return ps.executeUpdate() > 0;      
	    }
	}
    
    public static boolean modifierTotalCommande(int idCommande, float nouveauTotal) throws SQLException 
    {
    	//cette methode est appelee à la suppression et ajout de details commande
        String sql = "UPDATE Commande SET total = ? WHERE idCommande = ?";

        try (Connection cnx = Connexion.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) 
        {

            ps.setFloat(1, nouveauTotal);
            ps.setInt(2, idCommande);

            return ps.executeUpdate() > 0;
        }
    }
    
    static public boolean supprimerDetailsCommande(int idCommande, int idMedicament) throws SQLException
    {
        String sql = "DELETE FROM DetailsCommande WHERE idCommande = ? AND idMedicament = ?";
        
        try (Connection conn = Connexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, idCommande);
            ps.setInt(2, idMedicament);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } 
    }
	
	static public boolean annulerCommande(int idCommande) throws SQLException 
	{

	    String sql =
	        "UPDATE Commande SET etat = 'annulee' " +
	        "WHERE idCommande = ? AND etat = 'en cours'";
	    Connection conn = Connexion.getConnection();

	    try (PreparedStatement ps = conn.prepareStatement(sql)) 
	    {
	        ps.setInt(1, idCommande);
	        return ps.executeUpdate() > 0;
	    }
	}

	static public boolean recevoirCommande(int idCommande,LocalDate dateLivraisonReelle) throws SQLException 
	{
	    String sql =
	        "UPDATE Commande " +
	        "SET etat = 'recue', dateLivraisonReelle = ? " +
	        "WHERE idCommande = ? AND etat = 'en cours'";
	    Connection conn = Connexion.getConnection();

	    try (PreparedStatement ps = conn.prepareStatement(sql)) 
	    {
	        ps.setDate(1, Date.valueOf(dateLivraisonReelle));
	        ps.setInt(2, idCommande);

	        return ps.executeUpdate() > 0;
	    }
	}

	static public int creerCommande(int idFournisseur,LocalDate dateCommande,LocalDate dateLivraisonPrevue,float total) throws SQLException 
	{

        String sql =
            "INSERT INTO Commande " +
            "(idFournisseur, dateCommande, dateLivraisonPrevue, dateLivraisonReelle, total, etat) " +
            "VALUES (?, ?, ?,NULL, ?, 'en cours')";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {

            ps.setInt(1, idFournisseur);
            ps.setDate(2, java.sql.Date.valueOf(dateCommande));
            ps.setDate(3, java.sql.Date.valueOf(dateLivraisonPrevue));

            ps.setFloat(4, total);

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);//idCommande généré
            }
        }

        return -1;
    }

    static public boolean ajouterDetailsCommande(int idCommande,int idMedicament,int quantite,float prixUnitaireAchat) throws SQLException 
    {

        String sql =
            "INSERT INTO DetailsCommande " +
            "(idCommande, idMedicament, quantite, prixUnitaireAchat) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) 
        {

            ps.setInt(1, idCommande);
            ps.setInt(2, idMedicament);
            ps.setInt(3, quantite);
            ps.setFloat(4, prixUnitaireAchat);

            return ps.executeUpdate() == 1;
        }
    }
    
    ////////////////////////
    //MEDICAMENT
    static public int ajouterMedicament(String label, int quantite, String description, float prixUnitaire, int seuilMin) throws SQLException
    {
        String sql = "INSERT INTO Medicament (label, quantite, texteDesc, prixUnitaire, seuilMin) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Connexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            ps.setString(1, label);
            ps.setInt(2, quantite);
            ps.setString(3, description);
            ps.setFloat(4, prixUnitaire);
            ps.setInt(5, seuilMin);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) 
            {
                //recuperer ID généré
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) 
                {
                    return generatedKeys.getInt(1);
                }
            }
            return -1;    
        } 
    }
    
    public static boolean modifStock(int idMed,int q) throws SQLException
    {
    	String sql = "UPDATE Medicament SET quantite = ? WHERE idMedicament = ?";
    
	    try (Connection conn = Connexion.getConnection();
	        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
	    {
	    	Medicament m = MedicamentInterface.creerMed(idMed);
	        ps.setInt(1,m.getQuantite() + q);
	        ps.setInt(2,idMed);
	        
	        return ps.executeUpdate() == 1;
	           
	    } 
	    
    }
    
    /////////////////////////
    //VENTE/CLIENT
    public static int creerClient(String nom, String prenom, int tel) throws SQLException 
    {
	    String sql = "INSERT INTO Clients (nom, prenom, tel) VALUES (?, ?, ?)";
	
	    try (Connection conn = Connexion.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
	    {
	
	        ps.setString(1, nom);
	        ps.setString(2, prenom);
	        ps.setInt(3, tel);
	
	        ps.executeUpdate();
	
	        ResultSet keys = ps.getGeneratedKeys();
	        if (keys.next()) {
	            return keys.getInt(1); //idClient genere
	        }
    }
	    
    return -1;
    
    }

    public static int creerFactureVente(int idClient, LocalDate dateVente, float total) throws SQLException 
    {
	    String sql = "INSERT INTO FactureVente (idClient, dateVente, total) VALUES (?, ?, ?)";
	
	    try (Connection conn = Connexion.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
	    {
	
	        ps.setInt(1, idClient);
	        ps.setDate(2, Date.valueOf(dateVente));
	        ps.setFloat(3, total);
	
	        ps.executeUpdate();
	
	        ResultSet keys = ps.getGeneratedKeys();
	        if (keys.next()) {
	            return keys.getInt(1); //idVente genere
	        }
	    }
	    
	    return -1; 
    }

    public static String  creerDetailsVente(int idVente,int idMedicament,float prixUnitaireVente,int quantite) throws SQLException, StockInsuffisantException {

        Connection conn = Connexion.getConnection();
        int stockActuel = 0;
        int sm = 0;
        String msg = ""; //on l'affiche en cas d'alerte
        //verification du stock
        String checkStockSql = "SELECT quantite, seuilMin FROM Medicament WHERE idMedicament = ?";
        try (PreparedStatement psCheck = conn.prepareStatement(checkStockSql)) 
        {
            psCheck.setInt(1, idMedicament);
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) 
            {
                stockActuel = rs.getInt("quantite");
                sm = rs.getInt("seuilMin");
                
                //si le stock est suffisant
                if (stockActuel < quantite) 
                {
                    throw new StockInsuffisantException(
                        "Stock insuffisant pour le médicament ID " + idMedicament + 
                        ". Stock actuel: " + stockActuel + ", Quantité demandée: " + quantite
                    );
                }
                
            } 
            else 
            {
                throw new SQLException("Médicament introuvable avec l'ID: " + idMedicament);
            }
        }
        
        String sql = "INSERT INTO DetailsVente (idVente, idMedicament, prixUnitaireVente, quantite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, idVente);
            ps.setInt(2, idMedicament);
            ps.setFloat(3, prixUnitaireVente);
            ps.setInt(4, quantite);

            int rowsInserted = ps.executeUpdate();
            
            //mise à jour du stock
            if (rowsInserted > 0) 
            {
                String updateStockSql = "UPDATE Medicament SET quantite = quantite - ? WHERE idMedicament = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(updateStockSql)) {
                    psUpdate.setInt(1, quantite);
                    psUpdate.setInt(2, idMedicament);
                    psUpdate.executeUpdate();
                }
                
                //verification par rapport à seuil min
                if ((stockActuel - quantite) < sm) 
                {
                    msg = "Alerte ! Le médicament d'ID "+idMedicament+" est de de stock "+(stockActuel-quantite)+" sous le seuil minimum "+sm;
                }
            }

            return msg;
       }
    }
}
