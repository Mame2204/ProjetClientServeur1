package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import server.Article;
import server.IGestion;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Achat { 

	private JFrame frame;
	private JTable tlisteArticles;
	private JScrollPane scrollPane;
	private JTextField txtRef;
	private JTextField txtFamille;
	private JTextField txtPrix;
	private IGestion g;
	private double montant;
	static  ArrayList<Article> listeArticles = new ArrayList<Article>();
	
	public static ArrayList<Article> getListeArticles() {
	        return listeArticles;
	    }
	

    public static void setListeArticles(ArrayList<Article> listeArticles) {
        Achat.listeArticles = listeArticles;
    }

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
			//g=new GestionImpl();
			g= (IGestion) Naming.lookup("rmi://localhost:1940/gestion");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 946, 593);
		frame.getContentPane().setBackground(Color.decode("#85929E"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		this.montant=0.0;
		
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
		
		tlisteArticles = new JTable();
		tlisteArticles.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"R\u00E9ference", "Designation", "Prix", "Quantit\u00E9"
			}
		));
		tlisteArticles.setBounds(598, 100, 1, 1);
		
		scrollPane = new JScrollPane(tlisteArticles);
		scrollPane.setBounds(507, 93, 402, 284);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblListe = new JLabel("Panier");
		lblListe.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblListe.setBounds(507, 57, 254, 20);
		frame.getContentPane().add(lblListe);
		
		JLabel lblRef = new JLabel("R\u00E9ference :");
		lblRef.setBounds(28, 93, 101, 20);
		frame.getContentPane().add(lblRef);
		
		txtRef = new JTextField();
		txtRef.setBounds(165, 95, 146, 35);
		frame.getContentPane().add(txtRef);
		txtRef.setColumns(10);
		
		JButton btnchercher = new JButton("Chercher");
		btnchercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						rechercher();
			}
		});
		btnchercher.setBounds(345, 95, 101, 29);
		frame.getContentPane().add(btnchercher);
		
		JLabel lblNomDeFamille = new JLabel("Designation :");
		lblNomDeFamille.setBounds(28, 152, 119, 20);
		frame.getContentPane().add(lblNomDeFamille);
		
		txtFamille = new JTextField();
		txtFamille.setBounds(167, 149, 193, 35);
		frame.getContentPane().add(txtFamille);
		txtFamille.setColumns(10);
		
		JLabel lblPrix = new JLabel("Prix :");
		lblPrix.setBounds(30, 215, 69, 20);
		frame.getContentPane().add(lblPrix);
		
		txtPrix = new JTextField();
		txtPrix.setBounds(165, 212, 146, 35);
		frame.getContentPane().add(txtPrix);
		txtPrix.setColumns(10);
		
		JLabel lblQuantit = new JLabel("Quantit\u00E9 :");
		lblQuantit.setBounds(28, 274, 80, 20);
		frame.getContentPane().add(lblQuantit);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
		spinner.setBounds(165, 271, 146, 35);
		frame.getContentPane().add(spinner);
		
		JLabel label = new JLabel("0.0");
		label.setBounds(588, 402, 69, 20);
		frame.getContentPane().add(label);
		
		JButton btnAjouter = new JButton("Ajouter au panier");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String[][] table = null;
		        //String s=null;
		        String s=txtRef.getText();
		        
		            try {
		                 table = ((String[][])g.rechArticle(s));
		                 if (Integer.parseInt(table[0][4]) < (int)spinner.getValue()) {
		                     JOptionPane.showMessageDialog(null,
		                             "Pas de stock suffisant pour cet achat",
		                             "Avertissement",
		                             JOptionPane.WARNING_MESSAGE);
		                 } else {
		                  DefaultTableModel MonModel = (DefaultTableModel)tlisteArticles.getModel();
		                  MonModel.addRow(new Object[] {txtRef.getText(),txtFamille.getText(),txtPrix.getText(),spinner.getValue()});
		                  listeArticles.add(new Article(txtRef.getText(),txtFamille.getText(),Double.parseDouble(txtPrix.getText()),(int)spinner.getValue()));
		                  montant=montant+Double.parseDouble(txtPrix.getText())* (int)spinner.getValue();
		                  label.setText(String.valueOf(montant));
		                  txtRef.setText("");
		                  txtFamille.setText("");
		                  txtPrix.setText("");
		                  spinner.setValue(0);
		                 }
		            } 
		            catch (RemoteException e1) {
		                e1.printStackTrace();
		            }
		            
			}
		});
		btnAjouter.setBounds(161, 398, 166, 29);
		frame.getContentPane().add(btnAjouter);
		
		JButton btnPayer = new JButton("Payer");
		btnPayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    frame.setVisible(false);
				Paiement p=new Paiement();
				
			}
		});
		
		btnPayer.setBounds(362, 492, 115, 29);
		frame.getContentPane().add(btnPayer);
		
		JLabel lblTotal = new JLabel("Total :");
		lblTotal.setBounds(472, 395, 69, 35);
		frame.getContentPane().add(lblTotal);
		
		frame.setVisible(true);
		
	}
	
	private void rechercher() {
		String[][] table = null;
		String s=txtRef.getText();
		
    		try {
    			 table = ((String[][])g.rechArticle(s));
    			 if (table.length == 0) {
                     JOptionPane.showMessageDialog(null,
                     "Aucun article ne correspond à cette reference", "Avertissement",
                     JOptionPane.WARNING_MESSAGE);} 
               else if (Integer.parseInt(table[0][4]) == 0) {
                   JOptionPane.showMessageDialog(null,
                           "Pas de stock necessaire pour cet achat",
                           "Avertissement",
                           JOptionPane.WARNING_MESSAGE);
                   } 
                   else {
                       for(int i=0; i<5;i++) {
                            txtRef.setText(""+table[0][0]); //reference
                            txtFamille.setText(""+table[0][2]);  //Designation
                            txtPrix.setText(""+table[0][3]); //prix
                       }
                   }
    		} 
    		catch (RemoteException e) {
    			e.printStackTrace();
    		}
    	    
	}
	
}
