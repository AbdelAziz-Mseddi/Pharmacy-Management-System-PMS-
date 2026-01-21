package controle_interface;

import java.sql.*;
import connexion_sql.Connexion;

public class EmployeGestionInterface {

    public static int creerEmploye(String nom, String prenom, String tel, String mail, String mdp, String privilege) {
        String sqlInsert = "INSERT INTO Employe (nom, prenom, tel, mail, mdp, privilege) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection cnx = Connexion.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sqlInsert)) {

            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setInt(3, Integer.parseInt(tel));
            ps.setString(4, mail);
            ps.setString(5, mdp);
            ps.setString(6, privilege.toLowerCase());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                String sqlSelect = "SELECT idEmploye FROM Employe WHERE tel = ?";
                try (PreparedStatement ps2 = cnx.prepareStatement(sqlSelect)) {
                    ps2.setInt(1, Integer.parseInt(tel));
                    ResultSet rs = ps2.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("idEmploye");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
