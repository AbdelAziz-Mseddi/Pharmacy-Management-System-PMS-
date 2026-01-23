package classes_intermediaires;

public class PerformanceFournisseur 
{
    private int idFournisseur;
    private String entreprise;
    private int nbrAnnulation;
    private int nbrRetards;

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getEntreprise() {
        return entreprise;
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