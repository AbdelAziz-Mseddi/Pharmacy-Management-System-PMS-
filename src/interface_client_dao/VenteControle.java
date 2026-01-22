package interface_client_dao;

import connexion_sql.Connexion;
import java.sql.*;

public class VenteControle {
    public static int getStockDisponible(int idMedicament) {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cnx = Connexion.getConnection();
            String sql = "SELECT quantite FROM Medicament WHERE idMedicament = ?";
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, idMedicament);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("quantite");
            }
            return 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (cnx != null) cnx.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static float getPrixVente(int idMedicament) {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cnx = Connexion.getConnection();
            String sql = "SELECT prixUnitaire FROM Medicament WHERE idMedicament = ?";
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, idMedicament);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getFloat("prixUnitaire");
            }
            return 0f;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0f;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (cnx != null) cnx.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
