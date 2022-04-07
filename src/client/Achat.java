package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import server.GestionImpl;
import server.IGestion;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class Achat {

	private JFrame frame;
	private JTable tlisteArticles;
	private JScrollPane scrollPane;
	private JTextField txtRef;
	private JTextField txtFamille;
	private JTextField txtPrix;
	private JTextField txtQuantite;
	private IGestion g;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Achat window = new Achat();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Achat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			g=new GestionImpl();
			g= (IGestion) Naming.lookup("rmi://localhost:1910/gestion");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 946, 468);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.BOLD, 20));
		menuBar.setBackground(Color.CYAN);
		menuBar.setBounds(0, 0, 1191, 47);
		frame.getContentPane().add(menuBar);
		
		JMenu mnAccueil = new JMenu("Accueil");
		menuBar.add(mnAccueil);
		
		JMenu mnCaisse = new JMenu("Caisse");
		menuBar.add(mnCaisse);
		
		JMenuItem mntmAcheter = new JMenuItem("Acheter");
		mnCaisse.add(mntmAcheter);
		mntmAcheter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		tlisteArticles = new JTable();
		tlisteArticles.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Référence", "Famille", "Prix", "Quantité"
			}
		));
		tlisteArticles.setBounds(598, 100, 1, 1);
		
		scrollPane = new JScrollPane(tlisteArticles);
		scrollPane.setBounds(472, 95, 402, 256);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblListe = new JLabel("Liste des articles \u00E0 payer");
		lblListe.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblListe.setBounds(472, 59, 254, 20);
		frame.getContentPane().add(lblListe);
		
		JLabel lblRef = new JLabel("Reference :");
		lblRef.setBounds(28, 93, 101, 20);
		frame.getContentPane().add(lblRef);
		
		txtRef = new JTextField();
		txtRef.setBounds(119, 90, 146, 26);
		frame.getContentPane().add(txtRef);
		txtRef.setColumns(10);
		
		JButton btnchercher = new JButton("Chercher");
		btnchercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						rechercher();
			}
		});
		btnchercher.setBounds(280, 89, 101, 29);
		frame.getContentPane().add(btnchercher);
		
		JLabel lblNomDeFamille = new JLabel("Nom de famille :");
		lblNomDeFamille.setBounds(28, 152, 119, 20);
		frame.getContentPane().add(lblNomDeFamille);
		
		txtFamille = new JTextField();
		txtFamille.setBounds(167, 149, 193, 26);
		frame.getContentPane().add(txtFamille);
		txtFamille.setColumns(10);
		
		JLabel lblPrix = new JLabel("Prix :");
		lblPrix.setBounds(30, 215, 69, 20);
		frame.getContentPane().add(lblPrix);
		
		txtPrix = new JTextField();
		txtPrix.setBounds(91, 212, 146, 26);
		frame.getContentPane().add(txtPrix);
		txtPrix.setColumns(10);
		
		JLabel lblQuantit = new JLabel("Quantite :");
		lblQuantit.setBounds(28, 274, 80, 20);
		frame.getContentPane().add(lblQuantit);
		
		txtQuantite = new JTextField();
		txtQuantite.setBounds(119, 271, 146, 26);
		frame.getContentPane().add(txtQuantite);
		txtQuantite.setColumns(10);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 DefaultTableModel MonModel = (DefaultTableModel)tlisteArticles.getModel();
				 MonModel.addRow(new Object[] {txtRef.getText(),txtFamille.getText(),txtPrix.getText(),txtQuantite.getText()});
				 txtRef.setText("");
				 txtFamille.setText("");
				 txtPrix.setText("");
				 txtQuantite.setText("");
			}
		});
		btnAjouter.setBounds(150, 328, 115, 29);
		frame.getContentPane().add(btnAjouter);
		
		JButton btnPayer = new JButton("Payer");
		btnPayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPayer.setBounds(353, 367, 115, 29);
		frame.getContentPane().add(btnPayer);
		frame.setVisible(true);
		
	}
	
	private void rechercher() {
		
		String[][] table = null;
		String s=txtRef.getText();
		try {
			 table = ((String[][])g.getArticle(s));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<5;i++) {
			 txtRef.setText(""+table[0][0]);
			 txtFamille.setText(""+table[0][1]);
			 txtPrix.setText(""+table[0][2]);
		}
		
	}
}
