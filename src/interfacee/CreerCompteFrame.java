package user_interface;
import controle_interface.EmployeGestionInterface;
import javax.swing.*;
import java.awt.*;

public class CreerCompteFrame extends JFrame {

    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtTel;
    private JTextField txtMail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JComboBox<String> comboPrivilege;
    private JButton btnCreer;
    private JButton btnAnnuler;
    private JButton btnRetourLogin;

    public CreerCompteFrame() {

        setTitle("Créer un compte");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nom :"));
        txtNom = new JTextField(15);
        panel.add(txtNom);

        panel.add(new JLabel("Prénom :"));
        txtPrenom = new JTextField(15);
        panel.add(txtPrenom);

        panel.add(new JLabel("Téléphone :"));
        txtTel = new JTextField(15);
        panel.add(txtTel);

        panel.add(new JLabel("Email :"));
        txtMail = new JTextField(15);
        panel.add(txtMail);

        panel.add(new JLabel("Mot de passe :"));
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword);

        panel.add(new JLabel("Confirmer mot de passe :"));
        txtConfirmPassword = new JPasswordField(15);
        panel.add(txtConfirmPassword);

        panel.add(new JLabel("Privilège :"));
        comboPrivilege = new JComboBox<>(new String[]{
                "user",
                "Admin"
        });
        panel.add(comboPrivilege);

        btnCreer = new JButton("Créer");
        btnAnnuler = new JButton("Annuler");
        btnRetourLogin = new JButton("Retour au login");
        btnRetourLogin.setVisible(false);
        panel.add(btnCreer);
        panel.add(btnAnnuler);
        panel.add(btnRetourLogin);

        add(panel);

        // Actions
        btnAnnuler.addActionListener(e -> dispose());

        btnCreer.addActionListener(e -> creerCompte());
        
        btnRetourLogin.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void creerCompte() {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String tel = txtTel.getText();
        String mail = txtMail.getText();
        String pass = new String(txtPassword.getPassword());
        String confirm = new String(txtConfirmPassword.getPassword());
        String privilege = comboPrivilege.getSelectedItem().toString();

        // Vérifications
        if (nom.isEmpty() || prenom.isEmpty() || tel.isEmpty()
                || mail.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires");
            return;
        }

        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas");
            return;
        }

        int id = EmployeGestionInterface.creerEmploye(nom, prenom, tel, mail, pass, privilege);

        if (id != -1) {
            JOptionPane.showMessageDialog(this,
                    "Compte créé avec succès !\nID : " + id + "\nPrivilège : " + privilege);
            btnRetourLogin.setVisible(true);
            btnCreer.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erreur : impossible de créer le compte (email ou tel déjà existant)");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(CreerCompteFrame::new);
    }
}