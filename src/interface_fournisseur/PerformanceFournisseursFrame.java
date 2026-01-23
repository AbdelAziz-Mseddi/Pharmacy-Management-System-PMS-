package interface_fournisseur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import classes_intermediaires.PerformanceFournisseur;
import classes_principales.Admin;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import interface_fournisseur.RapportAnalyseFrame;

public class PerformanceFournisseursFrame extends JFrame 
{

    private JTable table;
    private JButton retour;

    public PerformanceFournisseursFrame() {

        //table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Fournisseur");
        model.addColumn("Entreprise");
        model.addColumn("Nbr Annulations");
        model.addColumn("Nbr Retards");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        //remplissage de la table
        try 
        {
        	ArrayList<PerformanceFournisseur> list = Admin.getPerformanceFournisseur();

        	for (PerformanceFournisseur p : list) {
        	    model.addRow(new Object[] {
        	        p.getIdFournisseur(),
        	        p.getEntreprise(),
        	        p.getNbrAnnulation(),
        	        p.getNbrRetards()
        	    });
        	}

        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this,"Erreur lors du chargement des données","Erreur",JOptionPane.ERROR_MESSAGE);
        }

       //panel bas
        retour = new JButton("Retour");
        JPanel panelBas = new JPanel();
        panelBas.add(retour);

        retour.addActionListener(e -> {
            new RapportAnalyseFrame().setVisible(true);
            this.dispose();
        });

        
        setLayout(new BorderLayout(10, 10));
        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        setTitle("Performance des Fournisseurs");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PerformanceFournisseursFrame().setVisible(true);
        });
    }*/
}
