package interface_client;

import classes_principales.Employe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AjouterClientFrame extends JFrame {

    private JTextField txtNom, txtPrenom, txtTel;
    private JButton btnCreer, btnRetour;
    private Employe employe;

    public AjouterClientFrame() {

        setTitle("Ajouter Nouveau Client");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nom:"), gbc);
        txtNom = new JTextField(20);
        gbc.gridx = 1;
        add(txtNom, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Prénom:"), gbc);
        txtPrenom = new JTextField(20);
        gbc.gridx = 1;
        add(txtPrenom, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Téléphone:"), gbc);
        txtTel = new JTextField(20);
        gbc.gridx = 1;
        add(txtTel, gbc);

        btnCreer = new JButton("Créer");
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnCreer, gbc);

        btnRetour = new JButton("Retour");
        btnRetour.setVisible(false);
        gbc.gridy = 4;
        add(btnRetour, gbc);

        btnCreer.addActionListener(e -> creerClient());

        btnRetour.addActionListener(e -> {
            VenteFrame venteFrame = new VenteFrame();
            venteFrame.setVisible(true);

            dispose();
        });
    }

    private void creerClient() {
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String telText = txtTel.getText().trim();

        if(nom.isEmpty() || prenom.isEmpty() || telText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires!", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int tel = Integer.parseInt(telText);
            int idClient = Employe.creerClient(nom, prenom, tel);

            if(idClient != -1) {
                JOptionPane.showMessageDialog(this, "Client créé avec succès! ID: " + idClient, "Succès", JOptionPane.INFORMATION_MESSAGE);

                btnRetour.setVisible(true);

                btnCreer.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la création du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le téléphone doit être un nombre valide!", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur SQL: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AjouterClientFrame().setVisible(true);
    }
}

