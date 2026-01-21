package controle_interface;

import java.sql.*;
import classes.Employe;
import connexion_sql.Connexion;

public class EmployeInterface {


    public static Employe login(int idEmploye, String motDePasse) {

        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnx = Connexion.getConnection();

            String sql = "SELECT * FROM Employe WHERE idEmploye = ? AND mdp = ?";
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, idEmploye); 
            ps.setString(2, motDePasse); 

            rs = ps.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("idEmploye");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int tel = rs.getInt("tel");
                String email = rs.getString("mail");
                String mdp = rs.getString("mdp");
                String privilege = rs.getString("privilege");

                return new Employe(id, nom, prenom, tel, email, mdp, privilege);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cnx != null) cnx.close(); } catch (Exception e) {}
        }

        return null;
    }
}
