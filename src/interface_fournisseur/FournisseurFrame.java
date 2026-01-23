package interface_fournisseur;

import javax.swing.*;

import classes_intermediaires.DetailsCommandeLabel;
import classes_principales.Commande;
import classes_principales.Employe;
import java.awt.*;
import interface_fournisseur_dao.CommandeInterface;
import user_interface.MenuFrame;
import exceptions_personnalisees.CommandeInexistanteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.SQLException;

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
    private String privilege;

    public FournisseurFrame(String privilege)
    {
    	this.privilege = privilege;
    	
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

        // ---- CENTRE : on force le panelInfos en haut ----
        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelInfos = new JPanel(new GridLayout(6, 2, 5, 8));

        panelCenter.add(panelInfos, BorderLayout.NORTH);
        panelCenter.add(Box.createVerticalGlue(), BorderLayout.CENTER);

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

        // ----- Boutons -----
        btnCreer = new JButton("Créer Commande");
        btnRetour = new JButton("Retour");
        btnModifier = new JButton("Modifier Commande");
        btnAnnuler = new JButton("Annuler Commande");
        btnRecevoir = new JButton("Recevoir Commande");

        btnCreer.setMaximumSize(new Dimension(180, 35));
        btnRetour.setMaximumSize(new Dimension(180, 35));
        btnModifier.setMaximumSize(new Dimension(180, 35));
        btnAnnuler.setMaximumSize(new Dimension(180, 35));
        btnRecevoir.setMaximumSize(new Dimension(180, 35));

        // Panel gauche : BoxLayout
        JPanel panelActions = new JPanel();
        panelActions.setLayout(new BoxLayout(panelActions, BoxLayout.Y_AXIS));
        panelActions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelActions.add(btnAnnuler);
        panelActions.add(Box.createRigidArea(new Dimension(0, 10)));
        panelActions.add(btnRecevoir);
        panelActions.add(Box.createRigidArea(new Dimension(0, 10)));
        panelActions.add(btnModifier);

        // Panel droit : BoxLayout
        JPanel panelDroite = new JPanel();
        panelDroite.setLayout(new BoxLayout(panelDroite, BoxLayout.Y_AXIS));
        panelDroite.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDroite.add(btnCreer);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(btnRetour);

        add(panelId, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelActions, BorderLayout.WEST);
        add(panelDroite, BorderLayout.EAST);

        txtId.addActionListener(e -> chargerCommande());

        btnCreer.addActionListener(e -> {
            new CreerCommandeFrame(privilege).setVisible(true);
            this.dispose();
        });

        btnModifier.addActionListener(e -> {
            new ModifierCommandeFrame(privilege).setVisible(true);
            this.dispose();
        });

        btnRetour.addActionListener(e -> {
            new MenuFrame(privilege).setVisible(true);
            this.dispose();
        });

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
                    JOptionPane.showMessageDialog(this, "Impossible d'annuler !","Erreur",JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "ID numérique requis !","Erreur",JOptionPane.ERROR_MESSAGE);
            }
            catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(this,"Erreur base de données !","Erreur",JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRecevoir.addActionListener(e -> {
            try
            {
                int id = Integer.parseInt(txtId.getText());

                JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
                JLabel lblDate = new JLabel("Date livraison (YYYY-MM-DD): ");
                JTextField txtDate = new JTextField(10);

                panel.add(lblDate);
                panel.add(txtDate);

                int result = JOptionPane.showConfirmDialog(this,panel,"Recevoir commande",JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION)
                {
                    Commande cmd = CommandeInterface.getCommandeId(id);
                    LocalDate date = LocalDate.parse(txtDate.getText());

                    if (date.isBefore(cmd.getDateCommande()))
                    {
                        JOptionPane.showMessageDialog(this, "Date livraison prévue doit être >= date commande !","Erreur",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    boolean test = Employe.recevoirCommande(id,date);

                    if (test)
                    {
                        JOptionPane.showMessageDialog(this, "Commande reçue");
                        afficherCommande(cmd);
                        //mise à jour du stock
                        ArrayList<DetailsCommandeLabel> d = CommandeInterface.getDetailsCommandeLabel(id);
                        
                        for(DetailsCommandeLabel l :d)
                        	Employe.modifStock(l.getIdMed(), l.getQuantite());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Impossible de recevoir !","Erreur",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "ID numérique requis !","Erreur",JOptionPane.ERROR_MESSAGE);
            }
            catch (java.time.format.DateTimeParseException ex)
            {
                JOptionPane.showMessageDialog(this, "Format date invalide !","Erreur",JOptionPane.ERROR_MESSAGE);
            }
            catch (CommandeInexistanteException ex)
            {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                viderAffichage();
            }
            catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(this,"Erreur base de données !","Erreur",JOptionPane.ERROR_MESSAGE);
            }
        });

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
            afficherCommande(c);
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this,"ID numérique requis !");
        }
        catch (CommandeInexistanteException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Erreur", JOptionPane.ERROR_MESSAGE);
            viderAffichage();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this,"Erreur base de données","Erreur", JOptionPane.ERROR_MESSAGE);
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

        if (c.getDateLivraisonActuelle() != null)
            valDla.setText(c.getDateLivraisonActuelle().toString());
        else
            valDla.setText("-");

        valTotal.setText(String.valueOf(c.getTotal()));
        valEtat.setText(c.getEtat());
    }

    /*public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            new FournisseurFrame("admin").setVisible(true);
        });
    }*/
}
