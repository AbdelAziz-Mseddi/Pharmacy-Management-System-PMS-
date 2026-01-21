package user_interface;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import classes.Medicament;
import classes.DetailsCommande;

import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeParseException;
import controle_interface.MedicamentInterface;


import classes.Commande;
import classes.Fournisseur;
import classes.Employe;
import controle_interface.FournisseurInterface;
import controle_interface.CommandeInterface;

public class CreerCommandeFrame extends JFrame
{
	//infos générales de la commande
	private JLabel lblIdf;
	private JLabel lblDc;
	private JLabel lblDlp;
	private JTextField txtIdf;
	private JTextField txtDc;
	private JTextField txtDlp;
	//date de livraison actuelle est null on le connait qu'à la réception ou bien à l'annulation
	//total et etat sont faits automatiquement par les methodes de l'Employe
	
	//infos pour le panier des details de commande
	private JLabel lblRecherche;
    private JTextField txtRecherche;
    private JButton btnRechercher; //user saisit le nom puis il choisit l'id du medicament voulu
    private JComboBox<Medicament> cbMedicaments;
	private JLabel lblQ;
	private JLabel lblP;
	private JTextField txtQ;
	private JTextField txtP;
	private JButton ajouterBtn;//ajouter au panier les details commande
	
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
	private JButton fournisseurBtn; //verifier le fournisseur est nouveau ou existant
	private Fournisseur fournisseurValide = null; //le res de la validité du fournisseur
	
	//Afficher à la fin id de la commande, l'etat et le total
	private JLabel lblIdc;
	private JLabel valIdc;
	private JLabel lblEtat;
	private JLabel valEtat;
	private JLabel lblT;
	private JLabel valT;
	
	
	private JButton validerBtn;
	private JButton retourBtn; //retour menu
	
	public CreerCommandeFrame()
	{
		//panel en haut: fournisseur
		JPanel panelFournisseur = new JPanel(new FlowLayout());
		lblIdf = new JLabel("ID Fournisseur: ");
		txtIdf = new JTextField(12);
		fournisseurBtn = new JButton("Vérifier Fournisseur");
		panelFournisseur.add(lblIdf);
		panelFournisseur.add(txtIdf);
		panelFournisseur.add(fournisseurBtn);
		
		lblE = new JLabel("Entreprise: ");
		lblTel = new JLabel("Téléphone: ");
		lblMail = new JLabel("Mail: ");
		valE = new JLabel("-");
		valTel = new JLabel("-");
		valMail = new JLabel("-");
		panelFournisseur.add(lblE);
		panelFournisseur.add(valE);
		panelFournisseur.add(lblTel);
		panelFournisseur.add(valTel);
		panelFournisseur.add(lblMail);
		panelFournisseur.add(valMail);
		
		//panel centré: commande 
		panier = new ArrayList<>();
		JPanel panelCommande = new JPanel(new FlowLayout());
		lblDc = new JLabel("Date Commande (YYYY-MM-DD): ");
		lblDlp = new JLabel("Date Livraison Prevue (YYYY-MM-DD): ");
		txtDc = new JTextField(10);
		txtDlp = new JTextField(10);
		
		panelCommande.add(lblDc);
		panelCommande.add(txtDc);
		panelCommande.add(lblDlp);
		panelCommande.add(txtDlp);

		
		//panel: panier
		JPanel panelPanier = new JPanel(new FlowLayout());
		
		lblRecherche = new JLabel("Recherche médicament : ");
        txtRecherche = new JTextField(12);
        btnRechercher = new JButton("Rechercher");

        cbMedicaments = new JComboBox<>();
        cbMedicaments.addItem(new Medicament(-1, "Choisir", 0, 0f, 0));

		lblQ = new JLabel("Quantité: ");
		txtQ = new JTextField(12);
		lblP = new JLabel("Prix: ");
		txtP = new JTextField(12);
		ajouterBtn = new JButton("Ajouter");
		
		panelPanier.add(lblRecherche);
        panelPanier.add(txtRecherche);
        panelPanier.add(btnRechercher);
        panelPanier.add(cbMedicaments); 
        
		panelPanier.add(lblQ);
		panelPanier.add(txtQ);
		panelPanier.add(lblP);
		panelPanier.add(txtP);

		panelPanier.add(ajouterBtn);
		
		//Table du panier
        modelPanier = new DefaultTableModel();
        modelPanier.addColumn("ID Medicament");
        modelPanier.addColumn("Nom");
        modelPanier.addColumn("Quantité");
        modelPanier.addColumn("Prix");
        
        tablePanier = new JTable(modelPanier);
        JScrollPane scrollPane = new JScrollPane(tablePanier);
		
		//panel bas: retoru + valider 
        JPanel panelBas = new JPanel(new FlowLayout());
		validerBtn = new JButton("Valider");
		retourBtn = new JButton("Retour");
		panelBas.add(validerBtn);
        panelBas.add(retourBtn);
        
        //Layout global
        setLayout(new BorderLayout());
        add(panelFournisseur, BorderLayout.NORTH);
        add(panelCommande, BorderLayout.CENTER);
        add(panelPanier, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.EAST);
        add(panelBas, BorderLayout.PAGE_END);
		
		
		fournisseurBtn.addActionListener(e -> chargerFournisseur());
		
		btnRechercher.addActionListener(e -> rechercherMedicament());

	    ajouterBtn.addActionListener(e -> ajouterAuPanier());
		
        retourBtn.addActionListener(e -> {
            new MenuFrame().setVisible(true);
            this.dispose();
        });
        
        validerBtn.addActionListener(e -> validerCommande());
        
		setTitle("Détails de la commande");
        setSize(1000, 500);;
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
	                JOptionPane.showMessageDialog(this,"ID inexistant");
	                viderAffichage();
	                new AjouterFournisseurFrame().setVisible(true);
	                this.dispose();
	                return;
	                
	            } 
	            
	            fournisseurValide = f;   //on stocke le fournisseur pour l'utiliser dans valider commande
	            afficherFournisseur(f);
	            
	        } 
		 catch (NumberFormatException e) 
		 {
	            JOptionPane.showMessageDialog(this,"ID numérique requis!");
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
     
        String nom = txtRecherche.getText().trim(); //lire le nom saisi et enlver les espaces au debut et à la fin
        
        cbMedicaments.removeAllItems();
        cbMedicaments.addItem(new Medicament(-1, "Choisir", 0, 0f, 0));

        ArrayList<Medicament> liste = MedicamentInterface.rechercherNom(nom);
        for (Medicament m : liste) 
        {
            cbMedicaments.addItem(m);
        }
    }
	
	private void ajouterAuPanier() 
	{
        Medicament m = (Medicament) cbMedicaments.getSelectedItem();
        if ((m == null)||(m.getIdMed() == -1)) 
        {
            JOptionPane.showMessageDialog(this, "Choisissez un médicament !");
            return;
        }

        try 
        {
            int q = Integer.parseInt(txtQ.getText());
            float p = Float.parseFloat(txtP.getText());
            
            if (q <= 0 || p <= 0)
            {
                JOptionPane.showMessageDialog(this, "Quantité et prix doivent être > 0 !");
                return;
            }
            
            DetailsCommande d = new DetailsCommande(m.getIdMed(),q,p);
            if(d.getIdMed() < 0)
            {
                JOptionPane.showMessageDialog(this, "Panier invalide !");
                return;
            }
            
            panier.add(d);

            modelPanier.addRow(new Object[]{
                    m.getIdMed(),
                    m.getLabel(),
                    q,
                    p
            });

        } 
        catch (NumberFormatException ex) 
        {
            JOptionPane.showMessageDialog(this, "Quantité et prix doivent être numériques !");
        }
    }
	
	private void validerCommande()
	{
		try 
        {
			if (fournisseurValide == null) 
			{
	            JOptionPane.showMessageDialog(this, "Vérifiez d'abord le fournisseur !");
	            return;
			}
            
        	int idf = Integer.parseInt(txtIdf.getText());
        	LocalDate dc = LocalDate.parse(txtDc.getText());
            LocalDate dlp = LocalDate.parse(txtDlp.getText());
            
            if (dlp.isBefore(dc)) 
            {
                JOptionPane.showMessageDialog(this, "Date livraison prévue doit être >= date commande !");
                return;
            }

            if (panier.isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Le panier est vide !");
                return;
            }
        	
            int idC = Employe.ajouterCommande(idf,dc,dlp);

            if(idC == -1)
            {
            	JOptionPane.showMessageDialog(this, "Impossible d'ajouter la commande !");
            	return;
            } 
            
            for (DetailsCommande d : panier) 
            {
                CommandeInterface.ajouterDetailsCommande(idC, d.getIdMed(), d.getQuantite(), d.getPrixAchat());
            }

            JOptionPane.showMessageDialog(this,"Commande ajoutée avec succès !\nID Commande : " + idC);
            new MenuFrame().setVisible(true);
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
		
	}
	
	

}
