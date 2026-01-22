package classes_principales;


public class Fournisseur
{
	
	private int idFournisseur;
	private String entreprise;
	private int tel;
	private String mail;
	
	
	public Fournisseur(int idF,String e,int t,String m)
	{
		idFournisseur = idF;
		mail = m;
		tel = t;
		entreprise = e;
	}

	public int getIdFournisseur()
	{
		return idFournisseur;
	}
	
	public String getMailFournisseur()
	{
		return mail;
	}
	
	public int getTelFournisseur()
	{
		return tel;
	}
	
	public String getEntrepriseFournisseur()
	{
		return entreprise;
	}
}