package classes;

import connexion_sql.Connexion;
import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;


public class Admin extends Employe 
{

    public static ArrayList<MedicamentAnalyse> getEtatStock(float marge) throws SQLException 
    {
        String sql = "SELECT idMedicament,label,quantite,seuilMin,\n" +
                "    CASE \n" +
                "        WHEN quantite = 0 THEN 'insufissant'\n" +
                "        WHEN quantite < (seuilMin - seuilMin * ?) THEN 'critique'\n" +
                "        WHEN quantite > (seuilMin * ? + seuilMin) THEN 'eleve'\n" +
                "        ELSE 'moyen'\n" +
                "    END AS etat_stock\n" +
                "FROM Medicament\n" +
                "order by etat_stock;";
        
        ArrayList<MedicamentAnalyse> m = new ArrayList<>();
        
        try (Connection conn = Connexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            ps.setFloat(1, marge);
            ps.setFloat(2, marge);
            ResultSet res = ps.executeQuery();
            //printResultSet(res);//for testing
            while(res.next())
            {
	            int i = res.getInt("idMedicament");
	            String l = res.getString("label");
	            int q = res.getInt("quantite");
	            int sm = res.getInt("seuilMin");
	            String e = res.getString("etat_stock");
	            MedicamentAnalyse ma = new MedicamentAnalyse(i,l,q,0,sm,e);
	            m.add(ma);
            }
        }
        
        return m;
    }
    public static float[] getChiffreAffaires(LocalDate date) throws SQLException 
    {
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
    public static ArrayList<PerformanceFournisseur> getPerformanceFournisseur() throws SQLException 
    {
    	ArrayList<PerformanceFournisseur> list = new ArrayList<>();
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
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            ResultSet res=ps.executeQuery();
            //printResultSet(res); //for testing
            while (res.next()) 
            {
	            PerformanceFournisseur pf = new PerformanceFournisseur();
	            pf.idFournisseur = res.getInt("idFournisseur");
	            pf.entreprise = res.getString("entreprise");
	            pf.nbrAnnulation = res.getInt("nbr_annulation");
	            pf.nbrRetards = res.getInt("nbr_retards");
	            list.add(pf);
            }
        }
        
        return list;
    }
    /*static void printResultSet(ResultSet rs) throws SQLException 
    { //for testing
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
    public static void main(String[] args) 
    {
        
        try {
            ResultSet r1 = Admin.getEtatStock(0.5f);
            //ResultSet r2 = Admin.getPerformanceFournisseur();
        }catch (SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getErrorCode());
            e.printStackTrace();

        }
    }*/
}

