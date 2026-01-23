package interface_fournisseur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import classes_intermediaires.DetailsCommandeLabel;
import classes_principales.Commande;
import classes_principales.Employe;
import classes_principales.Medicament;
import exceptions_personnalisees.CommandeInexistanteException;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.sql.SQLException;

import interface_fournisseur_dao.CommandeInterface;
import interface_fournisseur_dao.MedicamentInterface;

import user_interface.MenuFrame;

public class ModifierCommandeFrame extends JFrame
{
    private JTextField txtIdCommande;
    private JButton btnCharger;

    //affichage commande
    private JLabel valIdCmd;
    private JLabel valIdFour;
    private JLabel valDc;
    private JLabel valDlp;
    private JLabel valTotal;

    //modifier date
    private JTextField txtNewDlp;
    private JButton btnDlp;

    //panier
    private JTable tablePanier;
    private DefaultTableModel modelPanier;
    private ArrayList<DetailsCommandeLabel> panier;

    private JTextField txtRecherche;
    private JTextField txtQ;
    private JTextField txtP;
    private JComboBox<Medicament> cbMedicaments;
    private JButton btnAjouter;
    private JButton btnSupprimer;

    private JButton btnValider;
    private JButton btnRetour;

    private int idCommande = -1;
    
    private String privilege;

    public ModifierCommandeFrame(String pr)
    {
    	privilege = pr;
    	
    	setLayout(new BorderLayout());
    	
        panier = new ArrayList<>();

        //panel load
        JPanel panelLoad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLoad.setBorder(BorderFactory.createTitledBorder("Charger Commande"));

        panelLoad.add(new JLabel("ID Commande: "));
        txtIdCommande = new JTextField(12);
        panelLoad.add(txtIdCommande);

        btnCharger = new JButton("Charger");
        panelLoad.add(btnCharger);

        add(panelLoad, BorderLayout.NORTH);

        //affichage commande
        JPanel panelCommande = new JPanel(new GridLayout(5,2,10,5));
        panelCommande.setBorder(BorderFactory.createTitledBorder("Détails Commande"));

        valIdCmd = new JLabel("-");
        valIdFour = new JLabel("-");
        valDc = new JLabel("-");
        valDlp = new JLabel("-");
        valTotal = new JLabel("-");

        panelCommande.add(new JLabel("ID Commande: "));
        panelCommande.add(valIdCmd);
        panelCommande.add(new JLabel("ID Fournisseur: "));
        panelCommande.add(valIdFour);
        panelCommande.add(new JLabel("Date Commande: "));
        panelCommande.add(valDc);
        panelCommande.add(new JLabel("Date Livraison Prévue: "));
        panelCommande.add(valDlp);
        panelCommande.add(new JLabel("Total: "));
        panelCommande.add(valTotal);

        //panel modification
        JPanel panelModif = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelModif.setBorder(BorderFactory.createTitledBorder("Modifier la commande"));

        panelModif.add(new JLabel("Nouvelle date livraison prévue (YYYY-MM-DD): "));
        txtNewDlp = new JTextField(10);
        panelModif.add(txtNewDlp);
        btnDlp = new JButton("Modifier Date Livraison Prévue");
        panelModif.add(btnDlp);

        JPanel topCenter = new JPanel();
        topCenter.setLayout(new BoxLayout(topCenter, BoxLayout.Y_AXIS));
        topCenter.add(panelCommande);
        topCenter.add(panelModif);

        //panier
        modelPanier = new DefaultTableModel();
        modelPanier.addColumn("ID Med");
        modelPanier.addColumn("Nom");
        modelPanier.addColumn("Quantité");
        modelPanier.addColumn("Prix");

        tablePanier = new JTable(modelPanier);
        JScrollPane scroll = new JScrollPane(tablePanier);

        JPanel panelPanierLeft = new JPanel();
        panelPanierLeft.setLayout(new BoxLayout(panelPanierLeft, BoxLayout.Y_AXIS));
        panelPanierLeft.setBorder(BorderFactory.createTitledBorder("Modifier Panier"));

        JPanel rowR = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowR.add(new JLabel("Nom médicament:"));
        txtRecherche = new JTextField(10);
        rowR.add(txtRecherche);
        JButton btnRechercher = new JButton("Rechercher");
        rowR.add(btnRechercher);

        cbMedicaments = new JComboBox<>();
        cbMedicaments.addItem(new Medicament(-1,"Choisir",0,0f,0));

        JPanel rowQ = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowQ.add(new JLabel("Quantité:"));
        txtQ = new JTextField(6);
        rowQ.add(txtQ);

        JPanel rowP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowP.add(new JLabel("Prix:"));
        txtP = new JTextField(6);
        rowP.add(txtP);

        JPanel rowBtn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAjouter = new JButton("Ajouter");
        btnSupprimer = new JButton("Supprimer ligne");
        rowBtn.add(btnAjouter);
        rowBtn.add(btnSupprimer);

        panelPanierLeft.add(rowR);
        panelPanierLeft.add(cbMedicaments);
        panelPanierLeft.add(rowQ);
        panelPanierLeft.add(rowP);
        panelPanierLeft.add(rowBtn);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelPanierLeft,scroll);
        split.setDividerLocation(350);

        JPanel center = new JPanel(new BorderLayout());
        center.add(topCenter, BorderLayout.NORTH);
        center.add(split, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        //panel bas
        JPanel panelBas = new JPanel();
        btnValider = new JButton("Valider");
        btnRetour = new JButton("Retour");
        panelBas.add(btnValider);
        panelBas.add(btnRetour);
        add(panelBas, BorderLayout.SOUTH);

        btnDlp.addActionListener(e -> modifierDateLivraison());
        btnCharger.addActionListener(e -> chargerCommande());
        btnRechercher.addActionListener(e -> rechercherMedicament());
        btnAjouter.addActionListener(e -> ajouterAuPanier());
        btnSupprimer.addActionListener(e -> supprimerLigne());
        btnValider.addActionListener(e -> validerModification());
        btnRetour.addActionListener(e -> {
            dispose();
            new FournisseurFrame(privilege).setVisible(true);
        });

        setTitle("Modifier Commande");
        setSize(1050, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
    }

    private void chargerCommande()
    {
        try
        {
            
            idCommande = Integer.parseInt(txtIdCommande.getText());
	        Commande c = CommandeInterface.getCommandeId(idCommande);

            valIdCmd.setText(String.valueOf(idCommande));
            valIdFour.setText(String.valueOf(c.getIdFournisseur()));
            valDc.setText(c.getDateCommande().toString());
            valDlp.setText(c.getDateLivraisonPrevue().toString());
            valTotal.setText(String.valueOf(c.getTotal()));

            txtNewDlp.setText(c.getDateLivraisonPrevue().toString());

            chargerDetailsCommande();
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this,"ID numérique requis !");
        }
        catch (CommandeInexistanteException e)
	    {
	        JOptionPane.showMessageDialog(this, e.getMessage(),"Erreur", JOptionPane.ERROR_MESSAGE);
	    }
	    catch (SQLException e)
	    {
	        JOptionPane.showMessageDialog(this,"Erreur base de données","Erreur", JOptionPane.ERROR_MESSAGE);
	    }
    }

    private void chargerDetailsCommande()
    {
        panier.clear();
        modelPanier.setRowCount(0);

        ArrayList<DetailsCommandeLabel> details = CommandeInterface.getDetailsCommandeLabel(idCommande);
        
        /*System.out.println("ID commande = " + idCommande);
        System.out.println("Details size = " + details.size());*/

        for (DetailsCommandeLabel d : details)
        {
            panier.add(d);

            modelPanier.addRow(new Object[]{
                    d.getIdMed(),
                    d.getLabel(),
                    d.getQuantite(),
                    d.getPrixAchat()
            });
        }
    }

    private void modifierDateLivraison()
    {
        try
        {
            LocalDate nouvelleDate = LocalDate.parse(txtNewDlp.getText().trim());

            boolean test = Employe.modifierCommande(idCommande, nouvelleDate);

            if (!test)
            {
                JOptionPane.showMessageDialog(this,"Modification impossible !","Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,"Commande modifiée avec succès");
        }
        catch (DateTimeParseException e)
        {
            JOptionPane.showMessageDialog(this,"Format date invalide (YYYY-MM-DD)");
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this,"Erreur base de données","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechercherMedicament()
    {
        cbMedicaments.removeAllItems();
        cbMedicaments.addItem(new Medicament(-1,"Choisir",0,0f,0));

        for (Medicament m : MedicamentInterface.rechercherNom(txtRecherche.getText()))
            cbMedicaments.addItem(m);
    }

    private void ajouterAuPanier()
    {
        Medicament m = (Medicament) cbMedicaments.getSelectedItem();
        if (m == null || m.getIdMed() == -1) return;

        try
        {
            int q = Integer.parseInt(txtQ.getText());
            float p = Float.parseFloat(txtP.getText());

            Employe.ajouterDetailsCommande(idCommande, m.getIdMed(), q, p);

            //ajout dans le panier avec label
            panier.add(new DetailsCommandeLabel(m.getIdMed(), m.getLabel(), q, p));
            modelPanier.addRow(new Object[]{m.getIdMed(), m.getLabel(), q, p});
            
            mettreAJourAffichageTotal();
            float total = calculerTotal();
            Employe.modifierTotalCommande(idCommande, total);
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this,"Valeurs numériques !");
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Erreur base de données","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerLigne()
    {
        int row = tablePanier.getSelectedRow();

        if (row == -1)
        {
            JOptionPane.showMessageDialog(this,"Sélectionnez une ligne à supprimer","Erreur",JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idMed = (int) modelPanier.getValueAt(row, 0);

        try
        {
            boolean test = Employe.supprimerDetailsCommande(idCommande, idMed);

            if (!test)
            {
                JOptionPane.showMessageDialog(this,"Suppression impossible","Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }

            panier.remove(row);
            modelPanier.removeRow(row);
            mettreAJourAffichageTotal();
            float total = calculerTotal();
            Employe.modifierTotalCommande(idCommande, total);
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Erreur base de données","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validerModification()
    {
        JOptionPane.showMessageDialog(this, "Modifications enregistrées !");
    }
    
    private float calculerTotal() 
    {
        float total = 0f;
        for (DetailsCommandeLabel d : panier) {
            total += d.getQuantite() * d.getPrixAchat();
        }
        return total;
    }
    
    private void mettreAJourAffichageTotal()
    {
        float total = calculerTotal();
        valTotal.setText(String.valueOf(total));
    }
    
    /*public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            new ModifierCommandeFrame("admin").setVisible(true);
        });
    }*/
}
