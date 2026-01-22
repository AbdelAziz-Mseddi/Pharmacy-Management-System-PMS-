package classes_principales;

import java.time.LocalDate;

public class Vente {
    private int idVente;
    private int idClient;
    private int idMedicament;
    private LocalDate dateVente;
    private float prixUnitaireVente;
    private int quantite;
    private float total; // prixUnitaireVente * quantite

    public Vente(int idVente, int idClient, int idMedicament, LocalDate dateVente, float prixUnitaireVente, int quantite) {
        this.idVente = idVente;
        this.idClient = idClient;
        this.idMedicament = idMedicament;
        this.dateVente = dateVente;
        this.prixUnitaireVente = prixUnitaireVente;
        this.quantite = quantite;
        this.total = prixUnitaireVente * quantite;
    }

    public int getIdVente() { return idVente; }
    public int getIdClient() { return idClient; }
    public int getIdMedicament() { return idMedicament; }
    public LocalDate getDateVente() { return dateVente; }
    public float getPrixUnitaireVente() { return prixUnitaireVente; }
    public int getQuantite() { return quantite; }
    public float getTotal() { return total; }

    @Override
    public String toString() {
        return "Vente [idVente=" + idVente + ", idClient=" + idClient + ", idMedicament=" + idMedicament +
               ", dateVente=" + dateVente + ", prixUnitaireVente=" + prixUnitaireVente +
               ", quantite=" + quantite + ", total=" + total + "]";
    }
}
