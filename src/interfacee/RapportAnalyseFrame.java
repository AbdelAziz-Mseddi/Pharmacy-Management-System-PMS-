package user_interface;
import javax.swing.*;
import java.awt.*;
import controle_interface.CritiqueInterface;

public class RapportAnalyseFrame extends JFrame 
{
	private JButton ca; //chiffres d'affaires
	private JButton es; //etat des stocks
	private JButton pf; //performance des utilisateurs
	private JButton retour;
	
	public RapportAnalyseFrame()
	{
		//panel principal: en cas d'alerte le message ici sera affiché
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
		//panel des boutons
		JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4, 1, 10, 10));
        
        ca = new JButton("Chiffres d'Affaires");
        es = new JButton("Etat Stocks");
        pf = new JButton("Performance Fournisseurs");
        retour = new JButton("Retour");
        
        Dimension taille = new Dimension(200, 40);
        ca.setPreferredSize(taille);
        es.setPreferredSize(taille);
        pf.setPreferredSize(taille);
        retour.setPreferredSize(taille);

        btnPanel.add(ca);
        btnPanel.add(es);
        btnPanel.add(pf);
        btnPanel.add(retour);
		
        //message d'alerte
		boolean test = CritiqueInterface.stockCritique();
		
		if(!test)
		{
			JPanel alertPanel = new JPanel();
		    alertPanel.setLayout(new GridLayout(2, 1));

		    JLabel lbl1 = new JLabel("Alerte !");
		    JLabel lbl2 = new JLabel("Quantité sous le seuil minimal");

		    lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		    lbl2.setHorizontalAlignment(SwingConstants.CENTER);

		    alertPanel.add(lbl1);
		    alertPanel.add(lbl2);

		    panel.add(alertPanel, BorderLayout.NORTH);
		}
		
		panel.add(btnPanel, BorderLayout.CENTER);

        add(panel);
		
        retour.addActionListener(e -> {
            new MenuFrame("admin").setVisible(true);
            this.dispose();
        });
        
        ca.addActionListener(e -> {
            new ChiffresAffairesFrame().setVisible(true);
            this.dispose();
        });
        
        pf.addActionListener(e -> {
            new PerformanceFournisseursFrame().setVisible(true);
            this.dispose();
        });
        
        es.addActionListener(e -> {
            new EtatStocksFrame().setVisible(true);
            this.dispose();
        });
        
        setTitle("Les rapports d'analyse");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) 
	{
        SwingUtilities.invokeLater(() -> {
            new RapportAnalyseFrame().setVisible(true);
        });
    }
}
