package user_interface;
import javax.swing.*;
import java.awt.*;
import controle_interface.CommandeInterface;
import classes.Commande;
//import classes.Employe;

public class FournisseurFrame extends JFrame
{
	private JLabel lblId;
	private JTextField txtId; 
	private JButton btnCreer;
	private JButton btnModifier;
	private JButton btnAnnuler;
	private JButton btnRecevoir;
	private JButton btnRetour;
	private JLabel lblF;
    private JLabel lblDc;
    private JLabel lblDlp;
    private JLabel lblDla;
    private JLabel lblTotal;
    private JLabel lblEtat;
    private JLabel valF;
    private JLabel valDc;
    private JLabel valDlp;
    private JLabel valDla;
    private JLabel valTotal;
    private JLabel valEtat;
	
	public FournisseurFrame()
	{
		setLayout(new BorderLayout(10, 10));

		//Panel haut: ID
		JPanel panelId = new JPanel(new FlowLayout());
		lblId = new JLabel("ID Commande: ");
		panelId.add(lblId);
		txtId = new JTextField(15);
		panelId.add(txtId);
		
		//Panel centre haut: infos commande
		lblF = new JLabel("ID Fournisseur:");
	    lblDc = new JLabel("Date Commande:");
	    lblDlp = new JLabel("Date Livraison Prévue:");
	    lblDla = new JLabel("Date Livraison Actuelle:");
	    lblTotal = new JLabel("Total :");
	    lblEtat = new JLabel("État :");
	    
	    valF = new JLabel("-");
	    valDc = new JLabel("-");
	    valDlp = new JLabel("-");
	    valDla = new JLabel("-");
	    valTotal = new JLabel("-");
	    valEtat = new JLabel("-");

	    JPanel panelCenter = new JPanel(new GridBagLayout());
	    JPanel panelInfos = new JPanel(new GridLayout(6, 2, 5, 8));
	    panelCenter.add(panelInfos);
	    panelInfos.add(lblF);
	    panelInfos.add(valF);
	    panelInfos.add(lblDc);
	    panelInfos.add(valDc);
	    panelInfos.add(lblDlp);
	    panelInfos.add(valDlp);
	    panelInfos.add(lblDla);
	    panelInfos.add(valDla);
	    panelInfos.add(lblTotal);
	    panelInfos.add(valTotal);
	    panelInfos.add(lblEtat);
	    panelInfos.add(valEtat);
	    
	    Dimension btnSize = new Dimension(160, 5);

		//Panel gauche: boutons de gestion
		JPanel panelActions = new JPanel(new GridLayout(3, 1, 0, 10));
		btnModifier = new JButton("Modifier Commande");
		btnAnnuler = new JButton("Annuler Commande");
		btnRecevoir = new JButton("Recevoir Commande");
		btnModifier.setPreferredSize(btnSize);
		btnAnnuler.setPreferredSize(btnSize);
		btnRecevoir.setPreferredSize(btnSize);
		panelActions.add(btnAnnuler);
		panelActions.add(btnRecevoir);
		panelActions.add(btnModifier);
		
		//Panel droit: creer + retour
		JPanel panelDroite = new JPanel(new GridLayout(2, 1, 0, 10));
		btnCreer = new JButton("Créer Commande");
		btnRetour = new JButton("Retour");
	    btnCreer.setPreferredSize(btnSize);
	    btnRetour.setPreferredSize(btnSize);
		panelDroite.add(btnCreer);
		panelDroite.add(btnRetour);
		
		add(panelId, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelActions, BorderLayout.WEST);
		add(panelDroite, BorderLayout.EAST);
		
		//ActionListener
		txtId.addActionListener(e->chargerCommande());
		
		//Bouton creer
        btnCreer.addActionListener(e -> {
            new CreerCommandeFrame().setVisible(true);
            this.dispose();
        });

        //Bouton modifier
        btnModifier.addActionListener(e -> {
        	new ModifierCommandeFrame().setVisible(true);
            this.dispose();
        });

        //Bouton retour
        btnRetour.addActionListener(e -> {
            new MenuFrame().setVisible(true);
            this.dispose();
        });
        
        /*
        //Bouton annuler
        btnAnnuler.addActionListener(e -> {
            try 
            {
                int id = Integer.parseInt(txtId.getText());
                boolean test = Employe.annulerCommande(id);

                if(test) 
                {
                    JOptionPane.showMessageDialog(this, "Commande annulée");
                    viderAffichage();
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "Impossible d'annuler !");
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(this, "ID numérique requis !");
            }
        });
        
        //Bouton recevoir
        btnRecevoir.addActionListener(e -> {
            try 
            {
                int id = Integer.parseInt(txtId.getText());
                boolean test = Employe.recevoirCommande(id);

                if(test)
                {
                    JOptionPane.showMessageDialog(this, "Commande reçue");
                    afficherCommande(CommandeInterface.getCommandeId(id));
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "Impossible de recevoir !");
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(this, "ID numérique requis !");
            }
        });
		*/
		
		setTitle("Gérer les commandes fournisseurs");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void chargerCommande()
	{
		 try 
		 {
	            int id = Integer.parseInt(txtId.getText());
	            Commande c = CommandeInterface.getCommandeId(id);

	            if (c == null) 
	            {
	                JOptionPane.showMessageDialog(this,"ID inexistant");
	                viderAffichage();
	            } 
	            else 
	            {
	                afficherCommande(c);
	            }
	        } 
		 catch (NumberFormatException e) 
		 {
	            JOptionPane.showMessageDialog(this,"ID numérique requis!");
	     }
	}
	
	private void viderAffichage()
	{
		 valF.setText("-");
		 valDc.setText("-");
		 valDlp.setText("-");
		 valDla.setText("-");
		 valTotal.setText("-");
		 valEtat.setText("-");
	}
	
	public void afficherCommande(Commande c)
	{
		valF.setText(String.valueOf(c.getIdFournisseur()));
		valDc.setText(c.getDateCommande().toString());
		valDlp.setText(c.getDateLivraisonPrevue().toString());
		valDla.setText(c.getDateLivraisonActuelle().toString());
		valTotal.setText(String.valueOf(c.getTotal()));
		valEtat.setText(c.getEtat());
	}
	
	public static void main(String[] args) 
	{
        SwingUtilities.invokeLater(() -> {
            new FournisseurFrame().setVisible(true);
        });
    }
}

