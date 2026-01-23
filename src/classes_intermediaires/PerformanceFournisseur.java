package classes_intermediaires;

import classes_principales.Fournisseur;
public class PerformanceFournisseur extends Fournisseur
{
    
    private int nbrAnnulation;
    private int nbrRetards;

    
    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public int getNbrAnnulation() {
        return nbrAnnulation;
    }

    public void setNbrAnnulation(int nbrAnnulation) {
        this.nbrAnnulation = nbrAnnulation;
    }

    public int getNbrRetards() {
        return nbrRetards;
    }

    public void setNbrRetards(int nbrRetards) {
        this.nbrRetards = nbrRetards;
    }
}