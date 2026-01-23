package classes_principales;


public class Fournisseur
{
	//protected pas private car on a besoin d'accéder aux attributs dans la classe intermédiaire fille PerformanceFournisseur pour les setters
	
	protected int idFournisseur;
	protected String entreprise;
	protected int tel;
	protected String mail;
	
	public Fournisseur()
	{
		//ce constructeur juste sert pour la classe intermédiaire fille PerformanceFournisseur qui ne définit pas un construteur paramétré
	}
	
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