package user_interface;
import classes.Employe;
import controle_interface.EmployeInterface;
import javax.swing.*;
import java.awt.*;
public class LoginFrame extends JFrame {

    private JTextField txtId;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCreate;

    public LoginFrame() {

        setTitle("Pharmacie");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel(" Système de Gestion de Pharmacie", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(lblTitle, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));

        panelCenter.add(new JLabel("ID Employé :"));
        txtId = new JTextField(5);
        panelCenter.add(txtId);

        panelCenter.add(new JLabel("Mot de passe :"));
        txtPassword = new JPasswordField(5);
        panelCenter.add(txtPassword);

        panel.add(panelCenter, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnLogin = new JButton("Se connecter");
        btnCreate = new JButton("Créer un compte");

        panelBottom.add(btnLogin);
        panelBottom.add(btnCreate);

        panel.add(panelBottom, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        btnCreate.addActionListener(e -> creerCompte());
        btnLogin.addActionListener(e -> seconnecter());

        add(panel);
        setVisible(true);
    }
    private void seconnecter() {
    	String id = txtId.getText();
        String password = new String(txtPassword.getPassword());

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
            return;
        }

        int idInt = Integer.parseInt(id);
        Employe employe = EmployeInterface.login(idInt, password);

        if (employe != null) {
            JOptionPane.showMessageDialog(this,
                    "Connexion réussie !\nBienvenue " + employe.getNom() +
                            " (" + employe.getPrivilege() + ")");
        } else {
            JOptionPane.showMessageDialog(this, "ID ou mot de passe incorrect");
        }
    }

    private void creerCompte() {
         new CreerCompteFrame().setVisible(true);
         this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }

}