package interface_fournisseur_dao;
import java.sql.*;

import connexion_sql.Connexion;
import java.time.LocalDate;
import java.util.ArrayList;
import classes_intermediaires.DetailsCommandeLabel;
import classes_principales.Commande;
import exceptions_personnalisees.CommandeInexistanteException;

public class CommandeInterface
{

	public static Commande getCommandeId(int id) throws SQLException, CommandeInexistanteException
	{
	    String sql = "SELECT * FROM Commande WHERE idCommande = ?";
	    try (Connection cnx = Connexion.getConnection();
	         PreparedStatement ps = cnx.prepareStatement(sql)) 
	    {

	        ps.setInt(1, id);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (!rs.next()) 
	            {
	                throw new CommandeInexistanteException(
	                    "Commande avec ID " + id + " inexistante"
	                );
	            }

	            return new Commande(
	                rs.getInt("idCommande"),
	                rs.getInt("idFournisseur"),
	                rs.getDate("dateCommande").toLocalDate(),
	                rs.getDate("dateLivraisonPrevue").toLocalDate(),
	                rs.getDate("dateLivraisonReelle") == null ? null : rs.getDate("dateLivraisonReelle").toLocalDate(),
	                rs.getFloat("total"),
	                rs.getString("etat")
	            );
	        }
	    }
	}

    public static ArrayList<DetailsCommandeLabel> getDetailsCommandeLabel(int idCommande) 
    {
        ArrayList<DetailsCommandeLabel> list = new ArrayList<>();

        String sql = "SELECT d.idMedicament, m.label, d.quantite, d.prixUnitaireAchat " +
                     "FROM DetailsCommande d " +
                     "JOIN Medicament m ON d.idMedicament = m.idMedicament " +
                     "WHERE d.idCommande = ?";

        try (Connection cnx = Connexion.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) 
        {

            ps.setInt(1, idCommande);

            try (ResultSet rs = ps.executeQuery()) 
            {
                while (rs.next()) {
                    int idMed = rs.getInt("idMedicament");
                    String label = rs.getString("label");
                    int quantite = rs.getInt("quantite");
                    float prixAchat = rs.getFloat("prixUnitaireAchat");

                    list.add(new DetailsCommandeLabel(idMed, label, quantite, prixAchat));
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return list;
    }
}


