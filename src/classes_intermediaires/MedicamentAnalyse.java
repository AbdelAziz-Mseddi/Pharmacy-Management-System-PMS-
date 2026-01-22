package classes_intermediaires;

import classes_principales.Medicament;

public class MedicamentAnalyse extends Medicament 
{
	private String etat;
	public MedicamentAnalyse(int i,String s,int q,float p,int sm,String e)
	{
		super(i,s,q,p,sm);
		etat = e;
	}
	
	public String getEtatStock()
	{
		return etat;
	}

}
