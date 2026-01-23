package classes_intermediaires;

import classes_principales.DetailsCommande;

public class DetailsCommandeLabel extends DetailsCommande
{
	
	private String label;
	
	public DetailsCommandeLabel(int i,String s,int q,float p)
	{
		super(i,q,p);
		label = s;
	}
	
	public String getLabel()
	{
		return label;
	}
	
}
