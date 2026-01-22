package classes_principales;

public class DetailsVente {
    private int idMedicament;
    private int quantite;
    private float prixUnitaireVente;
    
    public DetailsVente(int idMedicament, int quantite, float prixUnitaireVente) {
        this.idMedicament = idMedicament;
        this.quantite = quantite;
        this.prixUnitaireVente = prixUnitaireVente;
    }
    
    // Getters
    public int getIdMedicament() {
        return idMedicament;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
    public float getPrixUnitaireVente() {
        return prixUnitaireVente;
    }
    
    // Setters
    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }
    
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public void setPrixUnitaireVente(float prixUnitaireVente) {
        this.prixUnitaireVente = prixUnitaireVente;
    }
    
    // Méthode utilitaire pour calculer le sous-total
    public float getSousTotal() {
        return quantite * prixUnitaireVente;
    }
    
    @Override
    public String toString() {
        return "DetailsVente{" +
                "idMedicament=" + idMedicament +
                ", quantite=" + quantite +
                ", prixUnitaireVente=" + prixUnitaireVente +
                ", sousTotal=" + getSousTotal() +
                '}';
    }
}