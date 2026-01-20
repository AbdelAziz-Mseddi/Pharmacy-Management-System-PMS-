package controle_interface;
import connexion_sql.Connexion;
import java.sql.*;
import classes.Fournisseur;


public class FournisseurInterface
{

    public static Fournisseur getFournisseurId(int i)
    {
    	Connection cnx = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {
        	cnx = Connexion.getConnection();
        	String sql = "SELECT * FROM Fournisseur WHERE idFournisseur = ?";
        	ps = cnx.prepareStatement(sql);
        	ps.setInt(1,i);
        	rs = ps.executeQuery();
        	
        	if (rs.next()) 
        	{
                int t = rs.getInt("tel");
                String e = rs.getString("entreprise");
                String m = rs.getString("mail");

                return new Fournisseur(i,e,t,m);
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

        return null;
    }
}


