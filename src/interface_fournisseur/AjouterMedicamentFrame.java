package interface_fournisseur;

import javax.swing.*;

import classes_principales.Employe;

import java.awt.*;
import java.sql.SQLException;

public class AjouterMedicamentFrame extends JFrame 
{

    private JTextField txtLabel;
    private JTextField txtQuantite;
    private JTextField txtDescription;
    private JTextField txtPrix;
    private JTextField txtSeuilMin;

    private JButton btnAjouter;
    private JButton btnRetour;

    public AjouterMedicamentFrame() 
    {

        setLayout(new BorderLayout(10, 10));

        //panel des champs
        JPanel panelChamps = new JPanel(new GridLayout(5, 2, 10, 15));
        panelChamps.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelChamps.add(new JLabel("Label: "));
        txtLabel = new JTextField();
        panelChamps.add(txtLabel);

        panelChamps.add(new JLabel("Quantité: "));
        txtQuantite = new JTextField();
        panelChamps.add(txtQuantite);

        panelChamps.add(new JLabel("Description: "));
        txtDescription = new JTextField();
        panelChamps.add(txtDescription);

        panelChamps.add(new JLabel("	Prix unitaire: "));
        txtPrix = new JTextField();
        panelChamps.add(txtPrix);

        panelChamps.add(new JLabel("	Seuil minimum: "));
        txtSeuilMin = new JTextField();
        panelChamps.add(txtSeuilMin);

        add(panelChamps, BorderLayout.CENTER);

        //panel des boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        btnAjouter = new JButton("Ajouter");
        btnRetour = new JButton("Retour");

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnRetour);

        add(panelBoutons, BorderLayout.SOUTH);

        btnAjouter.addActionListener(e -> ajouterMedicament());

        btnRetour.addActionListener(e -> {
            new CreerCommandeFrame().setVisible(true);
            this.dispose();
        });

        setTitle("Ajouter Médicament");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void ajouterMedicament() 
    {
        String label = txtLabel.getText().trim();
        String quantiteStr = txtQuantite.getText().trim();
        String description = txtDescription.getText().trim();
        String prixStr = txtPrix.getText().trim();
        String seuilMinStr = txtSeuilMin.getText().trim();

        if (label.isEmpty() || quantiteStr.isEmpty() || description.isEmpty() || prixStr.isEmpty() || seuilMinStr.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !","Erreur",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try 
        {
            int quantite = Integer.parseInt(quantiteStr);
            float prix = Float.parseFloat(prixStr);
            int seuilMin = Integer.parseInt(seuilMinStr);
            
            if(quantite == 0 || prix == 0 || seuilMin == 0) 
            {
                JOptionPane.showMessageDialog(this, "Valeurs numériques > 0 !", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Employe.ajouterMedicament(label, quantite, description, prix, seuilMin);

            if (id == -1) {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter le médicament !", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Médicament ajouté avec ID : " + id);
                new CreerCommandeFrame().setVisible(true);
                this.dispose();
            }

        } 
        catch (NumberFormatException ex) 
        {
            JOptionPane.showMessageDialog(this, "Quantité, prix et seuil minimum doivent être numériques !", "Erreur", JOptionPane.ERROR_MESSAGE);
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(this, "Erreur base de données : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AjouterMedicamentFrame().setVisible(true));
    }
}
