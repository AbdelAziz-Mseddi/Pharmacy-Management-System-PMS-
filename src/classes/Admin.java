package classes;

import connexion_sql.Connexion;
import java.time.LocalDate;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class Admin extends Employe {

    public ResultSet getEtatStock(float marge) throws SQLException {
        String sql = "SELECT idMedicament,label,quantite,seuilMin,\n" +
                "    CASE \n" +
                "        WHEN quantite = 0 THEN 'insufissant'\n" +
                "        WHEN quantite < (seuilMin - seuilMin * ?) THEN 'critique'\n" +
                "        WHEN quantite > (seuilMin * ? + seuilMin) THEN 'eleve'\n" +
                "        ELSE 'moyen'\n" +
                "    END AS etat_stock\n" +
                "FROM Medicament\n" +
                "order by etat_stock;";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setFloat(1, marge);
            ps.setFloat(2, marge);
            ResultSet res=ps.executeQuery();
            printResultSet(res);//for testing
            return res;
        }
    }
    public float[] getChiffreAffaires(LocalDate date) throws SQLException {
        String sql1 = "SELECT SUM(total) FROM Commande " +
                "WHERE dateLivraisonReelle > ? AND etat = 'recu'";
        String sql2 = "SELECT SUM(total) FROM FactureVente " +
                "WHERE dateVente > ?";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sql1);
             PreparedStatement ps2 = conn.prepareStatement(sql2)) {

            java.sql.Date d = java.sql.Date.valueOf(date);

            ps1.setDate(1, d);
            float depenses;
            try (ResultSet rs1 = ps1.executeQuery()) {
                rs1.next();
                depenses = rs1.getFloat(1);
            }

            ps2.setDate(1, d);
            float benefices;
            try (ResultSet rs2 = ps2.executeQuery()) {
                rs2.next();
                benefices = rs2.getFloat(1);
            }

            return new float[] { benefices, depenses, benefices - depenses };
        }
    }
    public ResultSet getPerformanceFournisseur() throws SQLException {
        String sql = "SELECT\n" +
                "  c.idFournisseur,f.entreprise,\n" +
                "  SUM(CASE WHEN c.etat = 'annulee' THEN 1 ELSE 0 END) AS nbr_annulation,\n" +
                "  SUM(CASE WHEN c.etat <> 'annulee'\n" +
                "            AND c.dateLivraisonReelle > c.dateLivraisonPrevue\n" +
                "           THEN 1 ELSE 0 END) AS nbr_retards\n" +
                "FROM Commande c,Fournisseur f\n" +
                "where c.idFournisseur=f.idFournisseur\n" +
                "GROUP BY c.idFournisseur;";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet res=ps.executeQuery();
            printResultSet(res); //for testing
            return res;
        }
    }
    static void printResultSet(ResultSet rs) throws SQLException { //for testing
        ResultSetMetaData md = rs.getMetaData();
        int cols = md.getColumnCount();

        // header (optional)
        for (int i = 1; i <= cols; i++) {
            if (i > 1) System.out.print(" | ");
            System.out.print(md.getColumnLabel(i));
        }
        System.out.println();

        // rows
        while (rs.next()) {
            for (int i = 1; i <= cols; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i)); // generic printing
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Admin Ad1=new Admin();
        try {
            ResultSet r1 = Ad1.getEtatStock(0.5f);
            ResultSet r2 = Ad1.getPerformanceFournisseur();
        }catch (SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getErrorCode());
            e.printStackTrace();

        }
    }



}