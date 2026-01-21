package user_interface;

import classes.Employe;
import javax.swing.*;

public class ClientsFrame extends JFrame {

    public ClientsFrame() {

        JButton btnHist = new JButton("Historique");
        JButton btnVente = new JButton("Nouvelle Vente");

        btnHist.addActionListener(e -> {
            new HistoriqueFrame().setVisible(true);
            this.dispose();
        });

        btnVente.addActionListener(e -> {
            new VenteFrame().setVisible(true);
            this.dispose();
        });

        

        JPanel p = new JPanel();
        p.add(btnHist);
        p.add(btnVente);

        add(p);
        setSize(300,150);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClientsFrame().setVisible(true);
        });
    }
}
