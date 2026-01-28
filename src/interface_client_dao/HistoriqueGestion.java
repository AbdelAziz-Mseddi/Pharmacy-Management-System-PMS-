package interface_client_dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import classes_principales.Vente;
import connexion_sql.Connexion;

public class HistoriqueGestion {
	
    public static List<Vente> getHistorique(String critere, String valeur, String order) {
        List<Vente> ventes = new ArrayList<>();
        String sql = "";

        switch (critere.toLowerCase()) {
            case "idvente":
                sql = "SELECT f.idVente, f.idClient, d.idMedicament, f.dateVente, d.prixUnitaireVente, d.quantite " +
                      "FROM FactureVente f JOIN DetailsVente d ON f.idVente = d.idVente " +
                      "WHERE f.idVente = ? ORDER BY (d.quantite * d.prixUnitaireVente) " + order;
                break;
            case "idclient":
                sql = "SELECT f.idVente, f.idClient, d.idMedicament, f.dateVente, d.prixUnitaireVente, d.quantite " +
                      "FROM FactureVente f JOIN DetailsVente d ON f.idVente = d.idVente " +
                      "WHERE f.idClient = ? ORDER BY (d.quantite * d.prixUnitaireVente) " + order;
                break;
            case "idmedicament":
                sql = "SELECT f.idVente, f.idClient, d.idMedicament, f.dateVente, d.prixUnitaireVente, d.quantite " +
                      "FROM FactureVente f JOIN DetailsVente d ON f.idVente = d.idVente " +
                      "WHERE d.idMedicament = ? ORDER BY (d.quantite * d.prixUnitaireVente) " + order;
                break;
            default: // "all"
                sql = "SELECT f.idVente, f.idClient, d.idMedicament, f.dateVente, d.prixUnitaireVente, d.quantite " +
                      "FROM FactureVente f JOIN DetailsVente d ON f.idVente = d.idVente ORDER BY (d.quantite * d.prixUnitaireVente) " + order;
        }

        try (Connection cnx = Connexion.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

        	if (!critere.equalsIgnoreCase("all")) {
        	    ps.setInt(1, Integer.parseInt(valeur));
        	}

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idVente = rs.getInt("idVente");
                int idClient = rs.getInt("idClient");
                int idMedicament = rs.getInt("idMedicament");
                LocalDate dateVente = rs.getDate("dateVente").toLocalDate();
                float prixUnitaireVente = rs.getFloat("prixUnitaireVente");
                int quantite = rs.getInt("quantite");

                ventes.add(new Vente(idVente, idClient, idMedicament, dateVente, prixUnitaireVente, quantite));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventes;
    }
}

