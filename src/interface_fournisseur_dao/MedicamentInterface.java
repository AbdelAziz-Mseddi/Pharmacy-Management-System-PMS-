package interface_fournisseur_dao;
import java.sql.*;
import java.util.ArrayList;

import classes_principales.Medicament;
import connexion_sql.Connexion;


public class MedicamentInterface
{

    public static ArrayList<Medicament> rechercherNom(String s)
    {
    	Connection cnx = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Medicament> m = new ArrayList<>();

        try
        {
        	cnx = Connexion.getConnection();
        	String sql = "SELECT * FROM Medicament WHERE LOWER(label) LIKE LOWER(?)"; //recherche insensible à la casse
;
        	ps = cnx.prepareStatement(sql);
        	ps.setString(1,s + "%");
        	rs = ps.executeQuery();
        	
        	while(rs.next()) 
        	{
        		
                int idM = rs.getInt("idMedicament");
                int q = rs.getInt("quantite");
                float p = rs.getFloat("prixUnitaire");
                String nom = rs.getString("label");
                int sm = rs.getInt("seuilMin");
                
                //System.out.println("AAA "+idM+" "+q+" "+p+" "+nom+" "+sm);
                
                Medicament med = new Medicament(idM,nom,q,p,sm);

                m.add(med);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        finally
		{
			try 
			{ 
				if (rs != null) 
					rs.close(); 
			}
			catch(Exception e)
			{
				
			}
			try 
			{ 
				if (ps != null)
					ps.close(); 
			}
			catch(Exception e)
			{
				
			}
			try 
			{ 
				if (cnx != null) 
					cnx.close(); 
			}
			catch(Exception e)
			{
				
			}
		}

        return m;
    }
}


