package classes_intermediaires;

public class DetailsCommandeLabel 
{
	private int idMed;
	private String label;
	private int quantite;
	private float prixAchat;
	
	public DetailsCommandeLabel(int i,String s,int q,float p)
	{
		idMed = i;
		label = s;
		quantite = q;
		prixAchat = p;
	}
	
	public int getIdMedicament()
	{
		return idMed;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public int getQuantite()
	{
		return quantite;
	}
	
	public float getPrixAchat()
	{
		return prixAchat;
	}

}
