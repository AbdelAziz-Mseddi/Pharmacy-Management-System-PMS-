package interface_fournisseur;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

import interface_fournisseur_dao.CommandeInterface;
import interface_fournisseur_dao.FournisseurInterface;
import interface_fournisseur_dao.MedicamentInterface;

import javax.swing.table.DefaultTableModel;

import classes_principales.Commande;
import classes_principales.DetailsCommande;
import classes_principales.Employe;
import classes_principales.Fournisseur;
import classes_principales.Medicament;

import java.time.format.DateTimeParseException;

import user_interface.MenuFrame;

import java.sql.SQLException;

public class CreerCommandeFrame extends JFrame
{
    //infos générales de la commande
    private JLabel lblIdf;
    private JLabel lblDc;
    private JLabel lblDlp;
    private JTextField txtIdf;
    private JTextField txtDc;
    private JTextField txtDlp;

    //infos pour le panier
    private JLabel lblRecherche;
    private JTextField txtRecherche;
    private JButton btnRechercher;
    private JComboBox<Medicament> cbMedicaments;
    private JLabel lblQ;
    private JLabel lblP;
    private JTextField txtQ;
    private JTextField txtP;
    private JButton ajouterBtn;

    //panier
    private JTable tablePanier;
    private DefaultTableModel modelPanier;
    private ArrayList<DetailsCommande> panier;

    //infos fournisseur
    private JLabel lblE;
    private JLabel lblTel;
    private JLabel lblMail;
    private JLabel valE;
    private JLabel valTel;
    private JLabel valMail;
    private JButton fournisseurBtn;
    private Fournisseur fournisseurValide = null;

    private JButton validerBtn;
    private JButton retourBtn;

    //bouton nouveau medicament
    private JButton nouveauMedBtn;

    public CreerCommandeFrame()
    {
        //panel en haut: fournisseur (vertical)
        JPanel panelFournisseur = new JPanel();
        panelFournisseur.setLayout(new BoxLayout(panelFournisseur, BoxLayout.Y_AXIS));
        panelFournisseur.setBorder(BorderFactory.createTitledBorder("Fournisseur"));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblIdf = new JLabel("ID Fournisseur: ");
        txtIdf = new JTextField(12);
        fournisseurBtn = new JButton("Vérifier Fournisseur");
        row1.add(lblIdf);
        row1.add(txtIdf);
        row1.add(fournisseurBtn);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblE = new JLabel("Entreprise: ");
        valE = new JLabel("-");
        row2.add(lblE);
        row2.add(valE);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTel = new JLabel("Téléphone: ");
        valTel = new JLabel("-");
        row3.add(lblTel);
        row3.add(valTel);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblMail = new JLabel("Mail: ");
        valMail = new JLabel("-");
        row4.add(lblMail);
        row4.add(valMail);

        panelFournisseur.add(row1);
        panelFournisseur.add(row2);
        panelFournisseur.add(row3);
        panelFournisseur.add(row4);

        //panel centré:commande
        panier = new ArrayList<>();
        JPanel panelCommande = new JPanel();
        panelCommande.setLayout(new BoxLayout(panelCommande, BoxLayout.Y_AXIS));
        panelCommande.setBorder(BorderFactory.createTitledBorder("Dates"));

        JPanel rowDate1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblDc = new JLabel("Date Commande (YYYY-MM-DD): ");
        txtDc = new JTextField(10);
        rowDate1.add(lblDc);
        rowDate1.add(txtDc);

        JPanel rowDate2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblDlp = new JLabel("Date Livraison Prevue (YYYY-MM-DD): ");
        txtDlp = new JTextField(10);
        rowDate2.add(lblDlp);
        rowDate2.add(txtDlp);

        panelCommande.add(rowDate1);
        panelCommande.add(rowDate2);

        //panel: panier
        JPanel panelPanier = new JPanel();
        panelPanier.setLayout(new BoxLayout(panelPanier, BoxLayout.Y_AXIS));
        panelPanier.setBorder(BorderFactory.createTitledBorder("Panier"));

        lblRecherche = new JLabel("Recherche médicament: ");
        txtRecherche = new JTextField(12);
        btnRechercher = new JButton("Rechercher");

        cbMedicaments = new JComboBox<>();
        cbMedicaments.addItem(new Medicament(-1, "Choisir", 0, 0f, 0));

        lblQ = new JLabel("Quantité: ");
        txtQ = new JTextField(12);
        lblP = new JLabel("Prix: ");
        txtP = new JTextField(12);
        ajouterBtn = new JButton("Ajouter");

        //btn visible si liste vide
        nouveauMedBtn = new JButton("Ajouter un nouveau médicament");
        nouveauMedBtn.setVisible(true); //au départ la liste est vide

        JPanel rowRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowRecherche.add(lblRecherche);
        rowRecherche.add(txtRecherche);
        rowRecherche.add(btnRechercher);

        JPanel rowCombo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowCombo.add(cbMedicaments);

        JPanel rowQ = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowQ.add(lblQ);
        rowQ.add(txtQ);

        JPanel rowP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowP.add(lblP);
        rowP.add(txtP);

        JPanel rowButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowButtons.add(ajouterBtn);
        rowButtons.add(nouveauMedBtn);

        panelPanier.add(rowRecherche);
        panelPanier.add(rowCombo);
        panelPanier.add(rowQ);
        panelPanier.add(rowP);
        panelPanier.add(rowButtons);


        //table: panier
        modelPanier = new DefaultTableModel();
        modelPanier.addColumn("ID Medicament");
        modelPanier.addColumn("Nom");
        modelPanier.addColumn("Quantité");
        modelPanier.addColumn("Prix");

        tablePanier = new JTable(modelPanier);
        JScrollPane scrollPane = new JScrollPane(tablePanier);

        //panel bas: retour + valider
        JPanel panelBas = new JPanel(new FlowLayout());
        validerBtn = new JButton("Valider");
        retourBtn = new JButton("Retour");
        panelBas.add(validerBtn);
        panelBas.add(retourBtn);

        setLayout(new BorderLayout());

        add(panelFournisseur, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(panelCommande);
        leftPanel.add(panelPanier);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
        split.setDividerLocation(650);
        split.setResizeWeight(0.6);

        add(split, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        fournisseurBtn.addActionListener(e -> chargerFournisseur());
        btnRechercher.addActionListener(e -> rechercherMedicament());
        ajouterBtn.addActionListener(e -> ajouterAuPanier());

        nouveauMedBtn.addActionListener(e -> {
            new AjouterMedicamentFrame().setVisible(true);
            this.dispose();
        });

        retourBtn.addActionListener(e -> {
            new MenuFrame("user").setVisible(true);
            this.dispose();
        });

        validerBtn.addActionListener(e -> validerCommande());

        setTitle("Détails de la Commande");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void chargerFournisseur()
    {
        try
        {
            int id = Integer.parseInt(txtIdf.getText());
            Fournisseur f = FournisseurInterface.getFournisseurId(id);

            if (f == null)
            {
                JOptionPane.showMessageDialog(this,"ID inexistant","Erreur",JOptionPane.ERROR_MESSAGE);
                viderAffichage();
                new AjouterFournisseurFrame().setVisible(true);
                this.dispose();
                return;
            }

            fournisseurValide = f;
            afficherFournisseur(f);

        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this,"ID numérique requis!","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viderAffichage()
    {
        txtIdf.setText("");
    }

    private void afficherFournisseur(Fournisseur f)
    {
        valE.setText(f.getEntrepriseFournisseur());
        valTel.setText(String.valueOf(f.getTelFournisseur()));
        valMail.setText(f.getMailFournisseur());
    }

    private void rechercherMedicament()
    {
        String nom = txtRecherche.getText().trim();

        cbMedicaments.removeAllItems();
        cbMedicaments.addItem(new Medicament(-1, "Choisir", 0, 0f, 0));

        ArrayList<Medicament> liste = MedicamentInterface.rechercherNom(nom);

        for (Medicament m : liste)
        {
            cbMedicaments.addItem(m);
        }

        //si la liste est vide on affiche le bouton ajouter 
        if (cbMedicaments.getItemCount() == 1) 
        {
            nouveauMedBtn.setVisible(true);
        } else {
            nouveauMedBtn.setVisible(false);
        }
    }

    private void ajouterAuPanier()
    {
        Medicament m = (Medicament) cbMedicaments.getSelectedItem();
        if ((m == null)||(m.getIdMed() == -1))
        {
            JOptionPane.showMessageDialog(this, "Choisissez un médicament !","Erreur",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            int q = Integer.parseInt(txtQ.getText());
            float p = Float.parseFloat(txtP.getText());

            if (q <= 0 || p <= 0)
            {
                JOptionPane.showMessageDialog(this, "Quantité et prix doivent être > 0 !","Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }

            DetailsCommande d = new DetailsCommande(m.getIdMed(),q,p);
            panier.add(d);

            modelPanier.addRow(new Object[]{m.getIdMed(),m.getLabel(),q,p});

        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Quantité et prix doivent être numériques !","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validerCommande()
    {
        try
        {
            if (fournisseurValide == null)
            {
                JOptionPane.showMessageDialog(this, "Vérifiez d'abord le fournisseur !","Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idf = Integer.parseInt(txtIdf.getText());
            LocalDate dc = LocalDate.parse(txtDc.getText().trim());
            LocalDate dlp = LocalDate.parse(txtDlp.getText().trim());

            if (dlp.isBefore(dc))
            {
                JOptionPane.showMessageDialog(this, "Date livraison prévue doit être >= date commande !","Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (panier.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Le panier est vide !","Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }

            float tot = calculerTotal();
            int idC = Employe.creerCommande(idf,dc,dlp,tot);

            if(idC == -1)
            {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter la commande !","Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (DetailsCommande d : panier)
            {
                Employe.ajouterDetailsCommande(idC, d.getIdMed(), d.getQuantite(), d.getPrixAchat());
            }

            JOptionPane.showMessageDialog(this,"Commande ajoutée avec succès avec ID Commande: " +idC+" et Facture: "+tot);
            new MenuFrame("user").setVisible(true);
            this.dispose();
        }
        catch(DateTimeParseException e)
        {
            JOptionPane.showMessageDialog(this, "Format de date invalide ! Utilisez YYYY-MM-DD");
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "ID numérique requis !");
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this, "Erreur base de données : " + ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    private float calculerTotal()
    {
        float tot = 0;
        for (DetailsCommande d : panier)
        {
            tot += d.getQuantite() * d.getPrixAchat();
        }
        return tot;
    }
}
