package user_interface;
import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame 
{

    private JButton btnClients;
    private JButton btnFournisseurs;
    private JButton btnRapports;

    public MenuFrame(String privilege) {

        
        btnClients = new JButton("Gestion Ventes");
        btnFournisseurs = new JButton("Gestion Commandes");
        btnRapports = new JButton("Rapports Analyse");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(btnClients);
        panel.add(btnFournisseurs);
        panel.add(btnRapports);

        add(panel);

        btnClients.addActionListener(e -> {
            new ClientFrame().setVisible(true);
            this.dispose();
        });

        btnFournisseurs.addActionListener(e -> {
            new FournisseurFrame().setVisible(true);
            this.dispose();
        });

        btnRapports.addActionListener(e -> {

            if(privilege.equals("admin")) 
            {
                new RapportAnalyseFrame().setVisible(true);
                this.dispose();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this,"Seuls les administrateurs peuvent y accéder","Accès refusé",JOptionPane.ERROR_MESSAGE);
            }
        });

        // Frame
        setTitle("Menu");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuFrame("admin").setVisible(true);
        });
    }
}
