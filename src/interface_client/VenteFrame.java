package interface_client;

import classes_principales.Client;
import classes_principales.Medicament;
import classes_principales.DetailsVente;
import classes_principales.Employe;
import exceptions_personnalisees.StockInsuffisantException;
import interface_client_dao.ClientControle;
import interface_client_dao.MedicamentInterface;
import interface_client_dao.VenteControle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class VenteFrame extends JFrame {
    private JTextField txtRecherche;
    private JList<String> listSuggestions;
    private DefaultListModel<String> listModel;
    private JComboBox<Integer> comboTelephones;
    private JButton btnAjouterClient;
    private Client clientSelectionne = null;
    
    private JLabel lblClientInfo;
    private JLabel valClientNom;
    private JLabel valClientTel;
    
    private JLabel lblRecherche;
    private JTextField txtRechercheMed;
    private JList<String> listSuggestionsMed;
    private DefaultListModel<String> listModelMed;
    private JLabel lblMedicamentSelectionne;
    private Medicament medicamentSelectionne = null;
    private JLabel lblQ;
    private JLabel lblP;
    private JTextField txtQ;
    private JLabel lblPrixAffiche;
    private JLabel lblStockDispo;
    private JButton ajouterBtn;
    
    private JTable tablePanier;
    private DefaultTableModel modelPanier;
    private ArrayList<DetailsVente> panier;
    private JLabel lblTotal;
    private JButton btnSupprimerLigne;
    
    private JButton validerBtn;
    private JButton retourBtn;
    
    public VenteFrame() {
        setTitle("Enregistrer une Vente");
        setSize(1150, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panier = new ArrayList<>();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelClient = new JPanel();
        panelClient.setLayout(new BoxLayout(panelClient, BoxLayout.Y_AXIS));
        panelClient.setBorder(BorderFactory.createTitledBorder("Sélection Client"));
        
        JPanel rowRechercheClient = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowRechercheClient.add(new JLabel("Rechercher client (nom/prénom): "));
        txtRecherche = new JTextField(20);
        rowRechercheClient.add(txtRecherche);
        btnAjouterClient = new JButton("Ajouter un client");
        rowRechercheClient.add(btnAjouterClient);
        
        JPanel rowSuggestions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        listModel = new DefaultListModel<>();
        listSuggestions = new JList<>(listModel);
        listSuggestions.setVisible(false);
        listSuggestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollClient = new JScrollPane(listSuggestions);
        scrollClient.setPreferredSize(new Dimension(300, 80));
        rowSuggestions.add(scrollClient);
        
        comboTelephones = new JComboBox<>();
        comboTelephones.setVisible(false);
        comboTelephones.setPreferredSize(new Dimension(200, 25));
        rowSuggestions.add(new JLabel("Téléphone: "));
        rowSuggestions.add(comboTelephones);
        
        JPanel rowClientInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblClientInfo = new JLabel("Client sélectionné: ");
        valClientNom = new JLabel("-");
        valClientNom.setFont(valClientNom.getFont().deriveFont(Font.BOLD));
        valClientNom.setForeground(new Color(0, 100, 0));
        valClientTel = new JLabel("");
        rowClientInfo.add(lblClientInfo);
        rowClientInfo.add(valClientNom);
        rowClientInfo.add(valClientTel);
        
        panelClient.add(rowRechercheClient);
        panelClient.add(rowSuggestions);
        panelClient.add(rowClientInfo);
        
        JPanel panelMedicament = new JPanel();
        panelMedicament.setLayout(new BoxLayout(panelMedicament, BoxLayout.Y_AXIS));
        panelMedicament.setBorder(BorderFactory.createTitledBorder("Ajouter des Médicaments"));
        
        lblRecherche = new JLabel("Recherche médicament: ");
        txtRechercheMed = new JTextField(20);
        
        JPanel rowRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowRecherche.add(lblRecherche);
        rowRecherche.add(txtRechercheMed);
        
        JPanel rowSuggestionsMed = new JPanel(new FlowLayout(FlowLayout.LEFT));
        listModelMed = new DefaultListModel<>();
        listSuggestionsMed = new JList<>(listModelMed);
        listSuggestionsMed.setVisible(false);
        listSuggestionsMed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollMed = new JScrollPane(listSuggestionsMed);
        scrollMed.setPreferredSize(new Dimension(300, 100));
        rowSuggestionsMed.add(scrollMed);
        
        JPanel rowMedSelectionne = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblMedicamentSelectionne = new JLabel("Médicament: -");
        lblMedicamentSelectionne.setFont(lblMedicamentSelectionne.getFont().deriveFont(Font.BOLD));
        rowMedSelectionne.add(lblMedicamentSelectionne);
        
        JPanel rowStock = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblStockDispo = new JLabel("Stock disponible: -");
        lblStockDispo.setFont(lblStockDispo.getFont().deriveFont(Font.ITALIC));
        rowStock.add(lblStockDispo);
        
        JPanel rowQ = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblQ = new JLabel("Quantité: ");
        txtQ = new JTextField(12);
        rowQ.add(lblQ);
        rowQ.add(txtQ);
        
        JPanel rowP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblP = new JLabel("Prix unitaire: ");
        lblPrixAffiche = new JLabel("-");
        lblPrixAffiche.setFont(lblPrixAffiche.getFont().deriveFont(Font.BOLD));
        rowP.add(lblP);
        rowP.add(lblPrixAffiche);
        
        JPanel rowButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ajouterBtn = new JButton("Ajouter au panier");
        rowButtons.add(ajouterBtn);
        
        panelMedicament.add(rowRecherche);
        panelMedicament.add(rowSuggestionsMed);
        panelMedicament.add(rowMedSelectionne);
        panelMedicament.add(rowStock);
        panelMedicament.add(rowQ);
        panelMedicament.add(rowP);
        panelMedicament.add(rowButtons);
        
        JPanel panelPanier = new JPanel(new BorderLayout(5, 5));
        panelPanier.setBorder(BorderFactory.createTitledBorder("Panier"));
        
        modelPanier = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelPanier.addColumn("ID Medicament");
        modelPanier.addColumn("Nom");
        modelPanier.addColumn("Quantité");
        modelPanier.addColumn("Prix");
        modelPanier.addColumn("Sous-total");
        
        tablePanier = new JTable(modelPanier);
        tablePanier.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablePanier.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablePanier.getColumnModel().getColumn(2).setPreferredWidth(70);
        tablePanier.getColumnModel().getColumn(3).setPreferredWidth(70);
        tablePanier.getColumnModel().getColumn(4).setPreferredWidth(90);
        
        JScrollPane scrollPanier = new JScrollPane(tablePanier);
        panelPanier.add(scrollPanier, BorderLayout.CENTER);
        
        JPanel panelPanierBas = new JPanel(new BorderLayout());
        
        btnSupprimerLigne = new JButton("Supprimer ligne");
        btnSupprimerLigne.setEnabled(false);
        JPanel panelSupprimer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSupprimer.add(btnSupprimerLigne);
        
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: 0.00 DT");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 16f));
        panelTotal.add(lblTotal);
        
        panelPanierBas.add(panelSupprimer, BorderLayout.WEST);
        panelPanierBas.add(panelTotal, BorderLayout.EAST);
        panelPanier.add(panelPanierBas, BorderLayout.SOUTH);
        
        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        validerBtn = new JButton("Valider la Vente");
        validerBtn.setPreferredSize(new Dimension(150, 35));
        validerBtn.setBackground(new Color(34, 139, 34));
        validerBtn.setForeground(Color.WHITE);
        retourBtn = new JButton("Retour");
        retourBtn.setPreferredSize(new Dimension(150, 35));
        panelBas.add(validerBtn);
        panelBas.add(retourBtn);
        
        mainPanel.add(panelClient, BorderLayout.NORTH);
        
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelMedicament, panelPanier);
        split.setDividerLocation(450);
        split.setResizeWeight(0.4);
        mainPanel.add(split, BorderLayout.CENTER);
        
        mainPanel.add(panelBas, BorderLayout.SOUTH);
        add(mainPanel);
        
        setupListeners();
    }
    
    private void setupListeners() {
        
        txtRecherche.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterClients(txtRecherche.getText());
            }
        });
        
        listSuggestions.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listSuggestions.getSelectedValue() != null) {
                    txtRecherche.setText(listSuggestions.getSelectedValue());
                    listSuggestions.setVisible(false);
                    showTelephonesForDuplicate(listSuggestions.getSelectedValue());
                }
            }
        });
        
        comboTelephones.addActionListener(e -> {
            if (comboTelephones.isVisible() && comboTelephones.getSelectedItem() != null) {
                selectionnerClient();
            }
        });
        
        btnAjouterClient.addActionListener(e -> {
            new AjouterClientFrame().setVisible(true);
            this.dispose();
        });
        
        
        txtRechercheMed.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterMedicaments(txtRechercheMed.getText());
            }
        });
        
        listSuggestionsMed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listSuggestionsMed.getSelectedValue() != null) {
                    String selected = listSuggestionsMed.getSelectedValue();
                    selectionnerMedicament(selected);
                }
            }
        });
        
        ajouterBtn.addActionListener(e -> ajouterAuPanier());
        
        
        tablePanier.getSelectionModel().addListSelectionListener(e -> {
            btnSupprimerLigne.setEnabled(tablePanier.getSelectedRow() != -1);
        });
        
        btnSupprimerLigne.addActionListener(e -> supprimerLignePanier());
        
        
        validerBtn.addActionListener(e -> validerVente());
        
        retourBtn.addActionListener(e -> {
            new ClientsFrame().setVisible(true);
            this.dispose();
        });
    }
    
    
    private void filterClients(String input) {
        listModel.clear();
        comboTelephones.setVisible(false);
        clientSelectionne = null;
        valClientNom.setText("-");
        valClientNom.setForeground(Color.BLACK);
        valClientTel.setText("");
        
        if (input.isEmpty()) {
            listSuggestions.setVisible(false);
            return;
        }
        
        List<Client> clients = ClientControle.getAllClients();
        for (Client c : clients) {
            String display = c.getNom() + " " + c.getPrenom();
            if (display.toLowerCase().contains(input.toLowerCase())) {
                listModel.addElement(display);
            }
        }
        
        listSuggestions.setVisible(!listModel.isEmpty());
        revalidate();
        repaint();
    }
    
    private void showTelephonesForDuplicate(String selected) {
        String[] parts = selected.split(" ");
        if (parts.length < 2) return;
        
        String nom = parts[0];
        String prenom = parts[1];
        
        List<Client> clients = ClientControle.getClients(nom, prenom);
        comboTelephones.removeAllItems();
        
        if (clients.size() > 1) {
            for (Client c : clients) {
                comboTelephones.addItem(c.getTel());
            }
            comboTelephones.setVisible(true);
        } else if (clients.size() == 1) {
            clientSelectionne = clients.get(0);
            afficherClientSelectionne();
        } else {
            comboTelephones.setVisible(false);
        }
        
        revalidate();
        repaint();
    }
    
    private void selectionnerClient() {
        String selected = txtRecherche.getText();
        String[] parts = selected.split(" ");
        if (parts.length < 2) return;
        
        String nom = parts[0];
        String prenom = parts[1];
        Integer tel = (Integer) comboTelephones.getSelectedItem();
        
        if (tel == null) return;
        
        List<Client> clients = ClientControle.getClients(nom, prenom);
        for (Client c : clients) {
            if (c.getTel() == tel) {
                clientSelectionne = c;
                afficherClientSelectionne();
                break;
            }
        }
    }
    
    private void afficherClientSelectionne() {
        if (clientSelectionne != null) {
            valClientNom.setText(clientSelectionne.getNom() + " " + clientSelectionne.getPrenom());
            valClientNom.setForeground(new Color(0, 100, 0));
            valClientTel.setText("(Tél: " + clientSelectionne.getTel() + ")");
        }
    }
    
    
    private void filterMedicaments(String input) {
        listModelMed.clear();
        medicamentSelectionne = null;
        lblMedicamentSelectionne.setText("Médicament: -");
        lblMedicamentSelectionne.setForeground(Color.BLACK);
        lblStockDispo.setText("Stock disponible: -");
        lblStockDispo.setForeground(Color.BLACK);
        lblPrixAffiche.setText("-");
        
        if (input.isEmpty()) {
            listSuggestionsMed.setVisible(false);
            return;
        }
        
        ArrayList<Medicament> liste = MedicamentInterface.rechercherNom(input);
        for (Medicament m : liste) {
            listModelMed.addElement(m.getLabel());
        }
        
        listSuggestionsMed.setVisible(!listModelMed.isEmpty());
        revalidate();
        repaint();
    }
    
    private void selectionnerMedicament(String nomMedicament) {
        txtRechercheMed.setText(nomMedicament);
        listSuggestionsMed.setVisible(false);
        
        ArrayList<Medicament> liste = MedicamentInterface.rechercherNom(nomMedicament);
        for (Medicament m : liste) {
            if (m.getLabel().equals(nomMedicament)) {
                medicamentSelectionne = m;
                afficherMedicamentSelectionne();
                break;
            }
        }
    }
    
    private void afficherMedicamentSelectionne() {
        if (medicamentSelectionne != null) {
            lblMedicamentSelectionne.setText("Médicament: " + medicamentSelectionne.getLabel());
            lblMedicamentSelectionne.setForeground(new Color(0, 100, 0));
            lblPrixAffiche.setText(String.format("%.2f DT", medicamentSelectionne.getPrix()));
            afficherStockDisponible(medicamentSelectionne.getIdMed());
        }
    }
    
    private void afficherStockDisponible(int idMedicament) {
        int stock = VenteControle.getStockDisponible(idMedicament);
        lblStockDispo.setText("Stock disponible: " + stock);
        
        if (stock == 0) {
            lblStockDispo.setForeground(Color.RED);
        } else if (stock < 10) {
            lblStockDispo.setForeground(new Color(255, 140, 0));
        } else {
            lblStockDispo.setForeground(new Color(0, 100, 0));
        }
    }
    
    private void ajouterAuPanier() {
        if (medicamentSelectionne == null) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un médicament !", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String qteText = txtQ.getText().trim();
            if (qteText.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Veuillez saisir une quantité !", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int q = Integer.parseInt(qteText);
            float p = medicamentSelectionne.getPrix();
            
            if (q <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "La quantité doit être supérieure à 0 !", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Vérifier le stock disponible
            int stockActuel = VenteControle.getStockDisponible(medicamentSelectionne.getIdMed());
            if (q > stockActuel) {
                JOptionPane.showMessageDialog(this, 
                    "Stock insuffisant ! Stock disponible: " + stockActuel, 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Vérifier si le médicament est déjà dans le panier
            for (int i = 0; i < panier.size(); i++) {
                if (panier.get(i).getIdMedicament() == medicamentSelectionne.getIdMed()) {
                    int response = JOptionPane.showConfirmDialog(this,
                        "Ce médicament est déjà dans le panier.\nVoulez-vous mettre à jour la quantité?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (response == JOptionPane.YES_OPTION) {
                        DetailsVente detail = panier.get(i);
                        int nouvelleQuantite = detail.getQuantite() + q;
                        
                        // Vérifier le stock pour la nouvelle quantité
                        if (nouvelleQuantite > stockActuel) {
                            JOptionPane.showMessageDialog(this, 
                                "Stock insuffisant ! Stock disponible: " + stockActuel, 
                                "Erreur", 
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        detail.setQuantite(nouvelleQuantite);
                        
                        modelPanier.setValueAt(detail.getQuantite(), i, 2);
                        modelPanier.setValueAt(String.format("%.2f", detail.getSousTotal()), i, 4);
                        
                        calculerTotal();
                        txtQ.setText("");
                    }
                    return;
                }
            }
            
            DetailsVente d = new DetailsVente(medicamentSelectionne.getIdMed(), q, p);
            panier.add(d);
            modelPanier.addRow(new Object[]{
                medicamentSelectionne.getIdMed(),
                medicamentSelectionne.getLabel(),
                q,
                String.format("%.2f", p),
                String.format("%.2f", d.getSousTotal())
            });
            
            calculerTotal();
            
            // Réinitialiser les champs
            txtQ.setText("");
            txtRechercheMed.setText("");
            medicamentSelectionne = null;
            lblMedicamentSelectionne.setText("Médicament: -");
            lblMedicamentSelectionne.setForeground(Color.BLACK);
            lblStockDispo.setText("Stock disponible: -");
            lblStockDispo.setForeground(Color.BLACK);
            lblPrixAffiche.setText("-");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "La quantité doit être un nombre entier valide !", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void supprimerLignePanier() {
        int selectedRow = tablePanier.getSelectedRow();
        if (selectedRow != -1) {
            int response = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer cette ligne?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION) {
                panier.remove(selectedRow);
                modelPanier.removeRow(selectedRow);
                calculerTotal();
                btnSupprimerLigne.setEnabled(false);
            }
        }
    }
    
    private void calculerTotal() {
        float tot = 0;
        for (DetailsVente d : panier) {
            tot += d.getSousTotal();
        }
        lblTotal.setText(String.format("Total: %.2f DT", tot));
    }
    
    private float getTotalFloat() {
        float tot = 0;
        for (DetailsVente d : panier) {
            tot += d.getSousTotal();
        }
        return tot;
    }
    
    
    private void validerVente() {
        if (clientSelectionne == null) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un client !", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            txtRecherche.requestFocus();
            return;
        }
        
        if (panier.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Le panier est vide !", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        float tot = getTotalFloat();
        
        int response = JOptionPane.showConfirmDialog(this,
            String.format("Confirmer la vente?\n\nClient: %s %s\nTotal: %.2f DT\nNombre d'articles: %d",
                clientSelectionne.getNom(),
                clientSelectionne.getPrenom(),
                tot,
                panier.size()),
            "Confirmation",
            JOptionPane.YES_NO_OPTION);
        
        if (response != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            LocalDate dateVente = LocalDate.now();
            
            int idVente = Employe.creerFactureVente(
                clientSelectionne.getIdClient(), 
                dateVente, 
                tot
            );
            
            if (idVente == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Impossible d'ajouter la vente !", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean allSuccess = true;
            StringBuilder errorMessages = new StringBuilder();
            
            for (DetailsVente d : panier) {
                try {
                    boolean success = Employe.creerDetailsVente(
                        idVente,
                        d.getIdMedicament(),
                        d.getPrixUnitaireVente(),
                        d.getQuantite()
                    );
                    
                    if (!success) {
                        allSuccess = false;
                        errorMessages.append("Erreur pour médicament ID ").append(d.getIdMedicament()).append("\n");
                    }
                } catch (StockInsuffisantException sie) {
                    allSuccess = false;
                    errorMessages.append(sie.getMessage()).append("\n");
                }
            }
            
            if (allSuccess) {
                JOptionPane.showMessageDialog(this, 
                    String.format("Vente ajoutée avec succès!\n\nID Vente: %d\nTotal: %.2f DT", idVente, tot),
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
                
                new ClientsFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreurs lors de l'enregistrement:\n" + errorMessages.toString(),
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erreur base de données : " + ex.getMessage(), 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VenteFrame().setVisible(true));
    }
}