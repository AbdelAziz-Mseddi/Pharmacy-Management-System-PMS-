package interface_fournisseur;

import javax.swing.*;

import classes_principales.Employe;

import java.awt.*;
import java.sql.SQLException;

public class AjouterFournisseurFrame extends JFrame 
{

    private JTextField txtEntreprise;
    private JTextField txtTel;
    private JTextField txtMail;
    private JButton btnAjouter;
    private JButton btnRetour;
    private String privilege;

    public AjouterFournisseurFrame(String privilege) 
    {
    	
    	this.privilege = privilege;

        setLayout(new BorderLayout(10, 10));

        //panel des champs
        JPanel panelChamps = new JPanel(new GridLayout(3, 2, 10, 15));
        panelChamps.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelChamps.add(new JLabel("Entreprise: "));
        txtEntreprise = new JTextField();
        panelChamps.add(txtEntreprise);

        panelChamps.add(new JLabel("Téléphone: "));
        txtTel = new JTextField();
        panelChamps.add(txtTel);

        panelChamps.add(new JLabel("Mail: "));
        txtMail = new JTextField();
        panelChamps.add(txtMail);

        add(panelChamps, BorderLayout.CENTER);

        //panel des boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        btnAjouter = new JButton("Ajouter");
        btnRetour = new JButton("Retour");

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnRetour);

        add(panelBoutons, BorderLayout.SOUTH);

        btnAjouter.addActionListener(e -> ajouterFournisseur());
        
        btnRetour.addActionListener(e -> {
            new CreerCommandeFrame(privilege).setVisible(true);
            this.dispose();
        });
        
        setTitle("Ajouter Fournisseur");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void ajouterFournisseur() 
    {
        String entreprise = txtEntreprise.getText().trim();
        String tel = txtTel.getText().trim();
        String mail = txtMail.getText().trim();

        if ((entreprise.isEmpty()) || (tel.isEmpty()) || (mail.isEmpty())) 
        {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !","Erreur",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try 
        {
            int id = Employe.ajouterFournisseur(entreprise,Integer.parseInt(tel), mail);

            if (id == -1) 
            {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter le fournisseur !","Erreur",JOptionPane.ERROR_MESSAGE);
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Fournisseur ajouté avec ID : " + id);
                new CreerCommandeFrame(privilege).setVisible(true);
                this.dispose();
            }
        }
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(this,"Erreur base de données !","Erreur",JOptionPane.ERROR_MESSAGE);
        }
        catch (NumberFormatException ex) 
        {
            JOptionPane.showMessageDialog(this,"Champ téléphone numérique !","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AjouterFournisseurFrame("user").setVisible(true));
    }*/
}
