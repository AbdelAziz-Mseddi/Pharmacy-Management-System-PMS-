package classes_principales;

public class DetailsCommande 
{
	//les champs sont protected pas private pour la classe fille intermédiare DetailsCommandeLabel
	
	//protected int idC;
    protected int idMed;
    protected int quantite;
    protected float prixAchat;
    
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
