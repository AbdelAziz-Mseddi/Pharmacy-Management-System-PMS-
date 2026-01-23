package user_interface;
import javax.swing.*;

import interface_fournisseur.FournisseurFrame;
import interface_fournisseur.RapportAnalyseFrame;
import interface_client.ClientsFrame;

import java.awt.*;

public class MenuFrame extends JFrame 
{
    private JButton btnClients;
    private JButton btnFournisseurs;
    private JButton btnRapports;
    private JButton btnLogout;
    public String privilege;

    public MenuFrame(String privilege) 
    {
        this.privilege = privilege;

        btnClients = new JButton("Gestion Ventes");
        btnFournisseurs = new JButton("Gestion Commandes");
        btnRapports = new JButton("Rapports Analyse");
        btnLogout = new JButton("Retour");

        JPanel panelCenter = new JPanel(new GridLayout(3, 1, 10, 10));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelCenter.add(btnClients);
        panelCenter.add(btnFournisseurs);
        panelCenter.add(btnRapports);

        JPanel panelBottom = new JPanel();
        panelBottom.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panelBottom.add(btnLogout);

        setLayout(new BorderLayout());
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        btnClients.addActionListener(e -> {
            new ClientsFrame(privilege).setVisible(true);
            dispose();
        });

        btnFournisseurs.addActionListener(e -> {
            new FournisseurFrame(privilege).setVisible(true);
            dispose();
        });

        btnRapports.addActionListener(e -> {
            if (privilege.equals("admin")) 
            {
                new RapportAnalyseFrame(privilege).setVisible(true);
                dispose();
            } 
            else 
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Seuls les administrateurs peuvent y accéder",
                    "Accès refusé",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setTitle("Menu");
        setSize(300, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuFrame("admin").setVisible(true);
        });
    }*/
}
