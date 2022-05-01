package client;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JTextField;

import server.GestionImpl;
import server.IGestion;
import serverSiege.GestSiegeImpl;
import serverSiege.IGestSiege;

import javax.swing.JTable;
import java.awt.Color;

public class Consult_Facture {

	private JFrame frame;
	private JLabel lblMontant;
	private JLabel lblDatefac;
	private JLabel lblCarteBanquaire;
	private JTextField txtFacture;
	private JTable table;
	private JScrollPane scrollPane;
	private IGestion g;
	String[][] detailsFact;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Consult_Facture window = new Consult_Facture();
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
	public Consult_Facture() {
		initialize();
	}
	
	 private void rechercher() {
	        table = new JTable();

	        table.setBounds(130, 135, 1, 1);
	        String s=txtFacture.getText();
	        String[] column= {"Reference","Famille","Prix","Quantite","Montant"};
	        
	        try {
	            table = new javax.swing.JTable((String[][])g.TrouveArticlesFacture(Integer.parseInt(s)),column);
	            detailsFact=g.TrouveFacture(Integer.parseInt(s));
	            
	        } catch (RemoteException e) {
	            e.printStackTrace();
	        }
	        lblDatefac.setText(""+detailsFact[0][1]);
	        lblCarteBanquaire.setText(""+detailsFact[0][2]);
	        lblMontant.setText(""+detailsFact[0][3]);
	        scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(67, 155, 718, 170);
	        frame.getContentPane().add(scrollPane);
	        
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
		frame.getContentPane().setBackground(Color.PINK);
		frame.setBounds(100, 100, 952, 554);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblReferenceFacture = new JLabel("Reference facture :");
		lblReferenceFacture.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblReferenceFacture.setBounds(97, 46, 188, 31);
		frame.getContentPane().add(lblReferenceFacture);
		
		txtFacture = new JTextField();
		txtFacture.setBounds(348, 44, 234, 37);
		frame.getContentPane().add(txtFacture);
		txtFacture.setColumns(10);
		
		 JButton btnChercher = new JButton("Chercher");
	        btnChercher.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	                rechercher();
	            }
	        });
	        btnChercher.setBounds(497, 59, 115, 34);
	        frame.getContentPane().add(btnChercher);
		
		JLabel lblDate = new JLabel("Date :");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDate.setBounds(97, 133, 69, 20);
		frame.getContentPane().add(lblDate);
		
		JLabel lblModePaiement = new JLabel("Mode paiement : ");
		lblModePaiement.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblModePaiement.setBounds(474, 133, 159, 20);
		frame.getContentPane().add(lblModePaiement);
		
		
		table = new JTable();
		table.setBounds(130, 135, 1, 1);
		String[] column= {"Reference","Famille","Quantite","Prix","Montant"};
		 
	    scrollPane = new JScrollPane(table);
		scrollPane.setBounds(70, 191, 767, 230);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblMontantTotal = new JLabel("Montant total :");
		lblMontantTotal.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMontantTotal.setBounds(350, 462, 159, 20);
		frame.getContentPane().add(lblMontantTotal);
		
		lblMontant = new JLabel("");
		lblMontant.setBounds(524, 463, 69, 20);
		frame.getContentPane().add(lblMontant);
		
		lblDatefac = new JLabel("");
		lblDatefac.setBounds(197, 134, 131, 20);
		frame.getContentPane().add(lblDatefac);
		
		lblCarteBanquaire = new JLabel("");
		lblCarteBanquaire.setBounds(670, 134, 142, 20);
		frame.getContentPane().add(lblCarteBanquaire);
	}

}