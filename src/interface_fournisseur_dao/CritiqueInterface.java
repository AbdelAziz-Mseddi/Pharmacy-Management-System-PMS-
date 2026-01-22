package interface_fournisseur_dao;
import connexion_sql.Connexion;
import java.sql.*;

import classes_principales.Fournisseur;


public class CritiqueInterface
{

    public static boolean stockCritique()
    {
    	Connection cnx = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {
        	cnx = Connexion.getConnection();
        	String sql = "SELECT idMedicament FROM Medicament WHERE quantite < seuilMin";
        	ps = cnx.prepareStatement(sql);
        	rs = ps.executeQuery();
        	
        	if (rs.next()) 
        	{
                return true;
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

        return false;
    }
}



