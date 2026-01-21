package classes;

public class Medicament 
{
	private int idMed;
    private String label;
    private int quantite;
    float prix;
    private int seuilMin;
    
    public Medicament(int i,String s,int q,float p,int sm)
    {
    	idMed = i;
        label = s;
        quantite = q;
        prix = p;
        seuilMin = sm;
    }
    
    public int getIdMed()
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
	
	public int getSeuilMin()
	{
		return seuilMin;
	}
	
	public float getPrix()
	{
		return prix;
	}
}

