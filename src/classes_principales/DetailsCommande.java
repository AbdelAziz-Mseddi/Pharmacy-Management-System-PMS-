package classes_principales;

public class DetailsCommande 
{
	//private int idC;
    private int idMed;
    private int quantite;
    private float prixAchat;
    
    public DetailsCommande(int m,int q,float p)
    {
    	//idC = i;
    	idMed = m;
        quantite = q;
        prixAchat = p;
    }
    
    public int getIdMed()
	{
		return idMed;
	}
	
	/*public int getIdCommande()
	{
		return idC;
	}*/
	
	public int getQuantite()
	{
		return quantite;
	}
	
	public float getPrixAchat()
	{
		return prixAchat;
	}
	
    

}
