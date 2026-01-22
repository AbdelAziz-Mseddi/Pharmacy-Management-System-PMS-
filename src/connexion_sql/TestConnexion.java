package connexion_sql;

public class TestConnexion
{
	public static void main(String[] args) 
	{
        try 
        {
            Connexion.getConnection();
            System.out.println("Connexion réussie !");
        } 
        catch (Exception e) 
        {
            System.out.println("Erreur de connexion !");
            e.printStackTrace();
        }
    }
}
