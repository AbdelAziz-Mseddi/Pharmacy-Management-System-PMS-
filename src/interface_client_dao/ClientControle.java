package interface_client_dao;

import classes_principales.Client;
import connexion_sql.Connexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientControle {

    public static List<Client> getClients(String nom, String prenom) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT idClient, nom, prenom, tel FROM Clients WHERE nom=? AND prenom=?";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, prenom);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idClient");
                String n = rs.getString("nom");
                String p = rs.getString("prenom");
                int tel = rs.getInt("tel");

                clients.add(new Client(id, n, p, tel));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT idClient, nom, prenom, tel FROM Clients ORDER BY nom, prenom";

        try (Connection conn = Connexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("idClient");
                String n = rs.getString("nom");
                String p = rs.getString("prenom");
                int tel = rs.getInt("tel");

                clients.add(new Client(id, n, p, tel));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
}

