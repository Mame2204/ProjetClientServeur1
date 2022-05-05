package client;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    try {
            //g=new GestionImpl();
            g= (IGestion) Naming.lookup("rmi://localhost:1940/gestion");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#85929E"));
		frame.setBounds(100, 100, 952, 554);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblReferenceFacture = new JLabel("Reference facture :");
		lblReferenceFacture.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblReferenceFacture.setBounds(97, 66, 188, 31);
		frame.getContentPane().add(lblReferenceFacture);
		
		txtFacture = new JTextField();
		txtFacture.setBounds(348, 64, 234, 37);
		frame.getContentPane().add(txtFacture);
		txtFacture.setColumns(10);
		
		 JButton btnChercher = new JButton("Chercher");
	        btnChercher.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	                rechercher();
	            }
	        });
	        btnChercher.setBounds(627, 64, 115, 37);
	        frame.getContentPane().add(btnChercher);
		
		JLabel lblDate = new JLabel("Date :");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDate.setBounds(97, 133, 69, 20);
		frame.getContentPane().add(lblDate);
		
		JLabel lblModePaiement = new JLabel("Mode paiement : ");
		lblModePaiement.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblModePaiement.setBounds(474, 133, 159, 20);
		frame.getContentPane().add(lblModePaiement);
		
		
		table = new JTable();
		table.setBounds(130, 135, 1, 1);
		String[] column= {"Reference","Designation","Quantite","Prix","Montant"};
		 
	    scrollPane = new JScrollPane(table);
		scrollPane.setBounds(70, 191, 767, 230);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblMontantTotal = new JLabel("Montant total :");
		lblMontantTotal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMontantTotal.setBounds(350, 462, 159, 20);
		frame.getContentPane().add(lblMontantTotal);
		
		lblMontant = new JLabel("0 €");
		lblMontant.setBounds(524, 463, 69, 20);
		frame.getContentPane().add(lblMontant);
		
		lblDatefac = new JLabel("");
		lblDatefac.setBounds(197, 134, 131, 20);
		frame.getContentPane().add(lblDatefac);
		
		lblCarteBanquaire = new JLabel("");
		lblCarteBanquaire.setBounds(670, 134, 142, 20); 
		frame.getContentPane().add(lblCarteBanquaire);
		
		JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        menuBar.setBackground(Color.CYAN);
        menuBar.setBounds(0, 0, 1191, 47);
        frame.getContentPane().add(menuBar);
        
        JMenu mnAccueil = new JMenu("Accueil");
        JMenuItem mntmAccueil = new JMenuItem("Retour Accueil");
        mnAccueil.add(mntmAccueil);
        mntmAccueil.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                InterfaceClient home=new InterfaceClient();
            }
        });
        menuBar.add(mnAccueil);
        
        
        JMenu mnCaisse = new JMenu("Caisse");
        menuBar.add(mnCaisse);
        
        JMenuItem mntmAcheter = new JMenuItem("Acheter");
        mnCaisse.add(mntmAcheter);
        mntmAcheter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Achat achat=new Achat();
                frame.setVisible(false);
            }
        });
        
        JMenu mnStock = new JMenu("Gestion");
        menuBar.add(mnStock);
        JMenuItem mntmStock = new JMenuItem("Ajout de stock");
        mnStock.add(mntmStock);
        mntmStock.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ModifierArticle mdifArt=new ModifierArticle();
                frame.setVisible(false);
            }
        });
        
        
        JMenuItem mntmMiseJour = new JMenuItem("Mise \u00E0 jour prix");
        mnStock.add(mntmMiseJour);
        mntmMiseJour.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    g.MiseAJour();
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                InterfaceClient home=new InterfaceClient();
                frame.setVisible(false);
            }
        });
        
        
        
        JMenu mnFacture = new JMenu("Facture");
        menuBar.add(mnFacture);

        JMenuItem mntmConsultationFacture = new JMenuItem("Consultation facture");
        mnFacture.add(mntmConsultationFacture);
        mntmConsultationFacture.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Consult_Facture consultFacture = new Consult_Facture();
                frame.setVisible(false);
            }
        });
        
        JMenu mnQuit = new JMenu("Quitter");
        mnQuit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int reponse=JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter ?", "confirm", JOptionPane.YES_NO_OPTION );
                if(reponse==JOptionPane.YES_OPTION) {
                    System.exit(0);;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
        menuBar.add(mnQuit);
		
		frame.setVisible(true);
	}
	private void rechercher() {
        table = new JTable();

        table.setBounds(130, 135, 1, 1);
        String s=txtFacture.getText();
        String[] column= {"Reference","Designation","Prix","Quantite","Montant"};
          
        try {
            Integer.parseInt(s);
            detailsFact=g.TrouveFacture(Integer.parseInt(s));
            table = new javax.swing.JTable((String[][])g.TrouveArticlesFacture(Integer.parseInt(s)),column);
            lblDatefac.setText(""+detailsFact[0][1]);
            lblCarteBanquaire.setText(""+detailsFact[0][2]);
            lblMontant.setText(""+detailsFact[0][3]+" €");
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(70, 191, 767, 230);
            frame.getContentPane().add(scrollPane);
            frame.setVisible(true);
        } catch (Exception e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Aucune facture ne correspond à cette reference", "Avertissement",
                    JOptionPane.WARNING_MESSAGE);
                    Consult_Facture cf=new Consult_Facture();
        }
        
        
        
    }

}

