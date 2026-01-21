package classes;

public class Client {

    private int idClient;
    private String nom;
    private String prenom;
    private int tel;

    public Client(int idClient, String nom, String prenom, int tel) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getTel() {
        return tel;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}

