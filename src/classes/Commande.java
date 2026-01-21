package classes;
import java.time.LocalDate;

public class Commande 
{
	private int idCommande;
	private int idFournisseur;
	private LocalDate dateCommande;
	private LocalDate dateLivraisonPrevue;
	private LocalDate dateLivraisonActuelle;
	private float total;
	private String etat;
	
	public Commande(int idC, int idF,LocalDate dc,LocalDate dlp,LocalDate dla,float t,String e)
	{
		idCommande = idC;
		idFournisseur = idF;
		dateCommande = dc;
		dateLivraisonPrevue = dlp;
		dateLivraisonActuelle = dla;
		total = t;
		etat = e;
	}
	
	public int getIdCommande()
	{
		return idCommande;
	}
	
	public int getIdFournisseur()
	{
		return idFournisseur;
	}
	
	public LocalDate getDateCommande()
	{
		return dateCommande;
	}
	
	public LocalDate getDateLivraisonPrevue()
	{
		return dateLivraisonPrevue;
	}
	
	public LocalDate getDateLivraisonActuelle()
	{
		return dateLivraisonActuelle;
	}
	
	public float getTotal()
	{
		return total;
	}
	
	public String getEtat()
	{
		return etat;
	}
}