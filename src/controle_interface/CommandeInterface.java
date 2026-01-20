package controle_interface;
import java.sql.*;
import classes.Commande;
import connexion_sql.Connexion;
import java.time.LocalDate;


public class CommandeInterface
{

    public static Commande getCommandeId(int i)
    {
    	Connection cnx = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {
        	cnx = Connexion.getConnection();
        	String sql = "SELECT * FROM Commande WHERE idCommande = ?";
        	ps = cnx.prepareStatement(sql);
        	ps.setInt(1,i);
        	rs = ps.executeQuery();
        	
        	if (rs.next()) 
        	{
                int idf = rs.getInt("idFournisseur");
                LocalDate dc = rs.getDate("dateCommande").toLocalDate();
                LocalDate dlp = rs.getDate("dateLivraisonPrevue").toLocalDate();
                LocalDate dla = rs.getDate("dateLivraisonReelle").toLocalDate();
                float total = rs.getFloat("total");
                String etat = rs.getString("etat");

                return new Commande(i,idf,dc,dlp,dla,total,etat);
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

