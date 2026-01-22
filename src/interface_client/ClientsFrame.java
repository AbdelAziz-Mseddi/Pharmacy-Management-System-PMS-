package interface_client;

import user_interface.MenuFrame;
import classes_principales.Employe;
import javax.swing.*;

public class ClientsFrame extends JFrame {
	
	private String privilege;

    public ClientsFrame(String pr) {
    	
    	privilege = pr;

        JButton btnHist = new JButton("Historique");
        JButton btnVente = new JButton("Nouvelle Vente");
        JButton btnRetour = new JButton("Retour");

        btnHist.addActionListener(e -> {
            new HistoriqueFrame(privilege).setVisible(true);
            this.dispose();
        });

        btnVente.addActionListener(e -> {
            new VenteFrame(privilege).setVisible(true);
            this.dispose();
        });
        
        btnRetour.addActionListener(e -> {
            new MenuFrame(privilege).setVisible(true);
            this.dispose();
        });

        

        JPanel p = new JPanel();
        p.add(btnHist);
        p.add(btnVente);
        p.add(btnRetour);

        add(p);
        setSize(300,150);
        setLocationRelativeTo(null);
    }
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {new ClientsFrame("user").setVisible(true);
        });
    }*/
}
