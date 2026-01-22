package interface_fournisseur;

import javax.swing.*;

import classes_principales.Admin;
import user_interface.MenuFrame;

import java.awt.*;
import java.time.LocalDate;

public class ChiffresAffairesFrame extends JFrame 
{

    private JLabel lbl;
    private JTextField txt;
    private JButton ca;
    private JButton retour;
    private JLabel lblBenefice;
    private JLabel valBenefice;
    private JLabel lblDepense;
    private JLabel valDepense;
    private JLabel lblDifference;
    private JLabel valDifference;

    public ChiffresAffairesFrame() 
    {

        //panel haut
        JPanel panelHaut = new JPanel(new GridLayout(3, 1, 0, 10));
        lbl = new JLabel("Date (YYYY-MM-DD): ");
        txt = new JTextField(10);
        ca = new JButton("Calculer");

        panelHaut.add(lbl);
        panelHaut.add(txt);
        panelHaut.add(ca);

        //panel milieu
        JPanel panelCentre = new JPanel(new GridLayout(6, 2, 10, 10));

        lblBenefice = new JLabel("Bénéfices: ");
        valBenefice = new JLabel("-");

        lblDepense = new JLabel("Dépenses: ");
        valDepense = new JLabel("-");

        lblDifference = new JLabel("Différence: ");
        valDifference = new JLabel("-");

        panelCentre.add(lblBenefice);
        panelCentre.add(valBenefice);

        panelCentre.add(lblDepense);
        panelCentre.add(valDepense);

        panelCentre.add(lblDifference);
        panelCentre.add(valDifference);

        //panel bas
        JPanel panelBas = new JPanel();
        retour = new JButton("Retour");
        panelBas.add(retour);

        //panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(panelHaut, BorderLayout.NORTH);
        panel.add(panelCentre, BorderLayout.CENTER);
        panel.add(panelBas, BorderLayout.SOUTH);

        add(panel);

        retour.addActionListener(e -> {
            new MenuFrame("admin").setVisible(true);
            this.dispose();
        });

        ca.addActionListener(e -> {
            try {
                LocalDate date = LocalDate.parse(txt.getText());

                float[] res = Admin.getChiffreAffaires(date);

                valBenefice.setText(String.valueOf(res[0]));
                valDepense.setText(String.valueOf(res[1]));
                valDifference.setText(String.valueOf(res[2]));

            } 
            catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(this,"Date invalide. Utilisez le format YYYY-MM-DD.","Erreur",JOptionPane.ERROR_MESSAGE);
            }
        });

        setTitle("Chiffres d'Affaires");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            new ChiffresAffairesFrame().setVisible(true);
        });
    }
}
