package user_interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import classes.Admin;
import classes.MedicamentAnalyse;

public class EtatStocksFrame extends JFrame {

    private JTable table;
    private JTextField txtMarge;
    private JButton btnAfficher;
    private JButton retour;

    public EtatStocksFrame() {

        //panel haut saisie marge
        JPanel panelHaut = new JPanel();
        panelHaut.add(new JLabel("Marge :"));
        txtMarge = new JTextField(5);
        panelHaut.add(txtMarge);

        btnAfficher = new JButton("Afficher");
        panelHaut.add(btnAfficher);

        //table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Médicament");
        model.addColumn("Quantité");
        model.addColumn("Seuil Min");
        model.addColumn("Etat Stock");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        //panel bas
        retour = new JButton("Retour");
        JPanel panelBas = new JPanel();
        panelBas.add(retour);

        btnAfficher.addActionListener(e -> {
            model.setRowCount(0); // vider la table

            try 
            {
                float marge = Float.parseFloat(txtMarge.getText());

                ArrayList<MedicamentAnalyse> list = Admin.getEtatStock(marge);

                for (MedicamentAnalyse m : list) 
                {
                    model.addRow(new Object[] {
                            m.getIdMed(),
                            m.getLabel(),
                            m.getQuantite(),
                            m.getSeuilMin(),
                            m.getEtatStock()
                    });
                }

            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(this,"Veuillez saisir une marge valide","Erreur",JOptionPane.ERROR_MESSAGE);
            } 
            catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(this,"Erreur lors du chargement des données","Erreur",JOptionPane.ERROR_MESSAGE);
            }
        });

        retour.addActionListener(e -> {
            new MenuFrame("admin").setVisible(true);
            this.dispose();
        });

       
        setLayout(new BorderLayout(10, 10));
        add(panelHaut, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        setTitle("Etat des Stocks");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            new EtatStocksFrame().setVisible(true);
        });
    }
}