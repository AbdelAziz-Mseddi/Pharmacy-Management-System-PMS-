package user_interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import classes.Vente;
import controle_interface.HistoriqueGestion;

public class HistoriqueFrame extends JFrame {

    private JTextField txtValeur;
    private JComboBox<String> comboCritere;
    private JComboBox<String> comboOrder;
    private JButton btnRechercher;
    private JTable tableHistorique;
    private DefaultTableModel tableModel;

    public HistoriqueFrame() {
        setTitle("Historique des ventes");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelTop = new JPanel(new FlowLayout());
        comboCritere = new JComboBox<>(new String[]{"all", "idVente", "idClient", "idMedicament"});
        txtValeur = new JTextField(10);
        comboOrder = new JComboBox<>(new String[]{"ASC", "DESC"});
        btnRechercher = new JButton("Rechercher");

        panelTop.add(new JLabel("Filtrer par :"));
        panelTop.add(comboCritere);
        panelTop.add(txtValeur);
        panelTop.add(new JLabel("Ordre :"));
        panelTop.add(comboOrder);
        panelTop.add(btnRechercher);

        String[] colonnes = {"ID Vente", "ID Client", "ID Médicament", "Quantité", "Prix Unitaire", "Total", "Date"};
        tableModel = new DefaultTableModel(colonnes, 0);
        tableHistorique = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tableHistorique);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnRechercher.addActionListener(e -> rechercher());

        setVisible(true);
    }

    private void rechercher() {
        String critere = comboCritere.getSelectedItem().toString();
        String valeur = txtValeur.getText().trim();
        String order = comboOrder.getSelectedItem().toString();

        if (critere.equalsIgnoreCase("all")) {
            valeur = null; 
        } else if (valeur.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir une valeur pour le critère sélectionné");
            return;
        }

        List<Vente> ventes = HistoriqueGestion.getHistorique(critere, valeur, order);


        tableModel.setRowCount(0);

        for (Vente v : ventes) {
            tableModel.addRow(new Object[]{
                    v.getIdVente(),
                    v.getIdClient(),
                    v.getIdMedicament(),
                    v.getQuantite(),
                    v.getPrixUnitaireVente(),
                    v.getTotal(),
                    v.getDateVente()
            });
        }

        if (ventes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun résultat trouvé !");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HistoriqueFrame::new);
    }
}

