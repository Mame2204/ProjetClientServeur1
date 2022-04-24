package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import server.Article;
import server.GestionImpl;
import server.IGestion;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JSpinner;

public class ModifierArticle {

	private JFrame frame;
	private JTextField txtRef;
	private JTextField txtFamille;
	private JTextField txtPrix;
	private IGestion g;
	private JTextField txtQuantite;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifierArticle window = new ModifierArticle();
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
	public ModifierArticle() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			g=new GestionImpl();
			g= (IGestion) Naming.lookup("rmi://localhost:1940/gestion");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 859, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblModificationDunArticle = new JLabel("Modification d'un article");
		lblModificationDunArticle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblModificationDunArticle.setBounds(335, 27, 266, 20);
		frame.getContentPane().add(lblModificationDunArticle);
		
		JLabel lblRef = new JLabel("R\u00E9f\u00E9rence: ");
		lblRef.setBounds(107, 99, 88, 20);
		frame.getContentPane().add(lblRef);
		
		JLabel lblFamille = new JLabel("Nom de famille: ");
		lblFamille.setBounds(107, 153, 119, 20);
		frame.getContentPane().add(lblFamille);
		
		JLabel lblPrix = new JLabel("Prix: ");
		lblPrix.setBounds(107, 207, 69, 20);
		frame.getContentPane().add(lblPrix);
		
		JLabel lblQuantite = new JLabel("Quantit\u00E9: ");
		lblQuantite.setBounds(107, 261, 88, 20);
		frame.getContentPane().add(lblQuantite);
		
		txtRef = new JTextField();
		txtRef.setBounds(288, 89, 184, 33);
		frame.getContentPane().add(txtRef);
		txtRef.setColumns(10);
		
		txtFamille = new JTextField();
		txtFamille.setBounds(288, 143, 184, 33);
		frame.getContentPane().add(txtFamille);
		txtFamille.setColumns(10);
		
		txtPrix = new JTextField();
		txtPrix.setBounds(288, 197, 184, 33);
		frame.getContentPane().add(txtPrix);
		txtPrix.setColumns(10);
		
		txtQuantite = new JTextField();
		txtQuantite.setBounds(288, 255, 184, 33);
		frame.getContentPane().add(txtQuantite);
		txtQuantite.setColumns(10);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Article a=new Article(txtRef.getText(), txtFamille.getText(),Double.parseDouble(txtPrix.getText()),Integer.parseInt(txtQuantite.getText()));
				try {
					boolean b=g.setArticle(a);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			InterfaceClient home=new InterfaceClient();
			frame.setVisible(false);
			}
		});
		btnModifier.setBounds(398, 359, 130, 39);
		frame.getContentPane().add(btnModifier);
		
		JButton btnChercher = new JButton("Chercher");
		btnChercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rechercher();
			}
		});
		btnChercher.setBounds(542, 89, 115, 30);
		frame.getContentPane().add(btnChercher);
		
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
			 txtQuantite.setText(""+table[0][3]);
		}
		
	}
}
