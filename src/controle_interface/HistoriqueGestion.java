package controle_interface;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import classes.Vente;
import connexion_sql.Connexion;

public class HistoriqueGestion {
	
    public static List<Vente> getHistorique(String critere, String valeur, String order) {
        List<Vente> ventes = new ArrayList<>();
        String sql = "";

        switch (critere.toLowerCase()) {
            case "idvente":
                sql = "SELECT f.idVente, f.idClient, d.idMedicament, f.dateVente, d.prixUnitaireVente, d.quantite " +
                      "FROM FactureVente f JOIN DetailsVente d ON f.idVente = d.idVente " +
                      "WHERE f.idVente = ? ORDER BY f.idVente " + order;
                break;
            case "idclient":
                sql = "SELECT f.idVente, f.idClient, d.idMedicament, f.dateVente, d.prixUnitaireVente, d.quantite " +
                      "FROM FactureVente f JOIN DetailsVente d ON f.idVente = d.idVente " +
                      "WHERE f.idClient = ? ORDER BY f.idClient " + order;
                break;
            case "idmedicament":
                sql = "SELECT f.idVente, f.idClient, d.idMedicament, f.dateVente, d.prixUnitaireVente, d.quantite " +
                      "FROM FactureVente f JOIN DetailsVente d ON f.idVente = d.idVente " +
                      "WHERE d.idMedicament = ? ORDER BY d.idMedicament " + order;
                break;
            default: // "all"
                sql = "SELECT f.idVente, f.idClient, d.idMedicament, f.dateVente, d.prixUnitaireVente, d.quantite " +
                      "FROM FactureVente f JOIN DetailsVente d ON f.idVente = d.idVente ORDER BY f.dateVente " + order;
        }

        try (Connection cnx = Connexion.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            if (!critere.equalsIgnoreCase("all")) {
                ps.setString(1, valeur);
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

