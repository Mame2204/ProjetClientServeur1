package client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import server.Article;
import server.GestionImpl;
import server.IGestion;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private JSpinner spinner;
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

	
	private void initialize() {
		try {
			//g=new GestionImpl();
			g= (IGestion) Naming.lookup("rmi://localhost:1940/gestion");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 859, 534);
		frame.getContentPane().setBackground(Color.decode("#85929E"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblModificationDunArticle = new JLabel("Ajout de stock");
		lblModificationDunArticle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblModificationDunArticle.setBounds(335, 67, 266, 20);
		frame.getContentPane().add(lblModificationDunArticle);
		JLabel lblModificationDunArticle1 = new JLabel("veillez saisir la reference de l'article concerné, puis cliquez sur rechercher");
		JLabel lblRef = new JLabel("R\u00E9f\u00E9rence: ");
		lblRef.setBounds(107, 139, 88, 20);
		frame.getContentPane().add(lblRef);
		
		JLabel lblFamille = new JLabel("Designation: ");
		lblFamille.setBounds(107, 193, 119, 20);
		frame.getContentPane().add(lblFamille);
		
		JLabel lblPrix = new JLabel("Prix: ");
		lblPrix.setBounds(107, 247, 69, 20);
		frame.getContentPane().add(lblPrix);
		
		JLabel lblQuantite = new JLabel("Quantit\u00E9: ");
		lblQuantite.setBounds(107, 301, 88, 20);
		frame.getContentPane().add(lblQuantite);
		
		txtRef = new JTextField();
		txtRef.setBounds(288, 129, 184, 33);
		frame.getContentPane().add(txtRef);
		txtRef.setColumns(10);
		
		txtFamille = new JTextField();
		txtFamille.setBounds(288, 183, 184, 33);
		frame.getContentPane().add(txtFamille);
		txtFamille.setColumns(10);
		txtFamille.setEditable(false);
		
		txtPrix = new JTextField();
		txtPrix.setBounds(288, 237, 184, 33);
		frame.getContentPane().add(txtPrix);
		txtPrix.setColumns(10);
		txtPrix.setEditable(false);
		
		spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        spinner.setBounds(288, 295, 184, 33);
        frame.getContentPane().add(spinner);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String[][] table = null;
		        String s=txtRef.getText();
		        try {
		                Article a=new Article(txtRef.getText(), txtFamille.getText(),Double.parseDouble(txtPrix.getText()),(int)spinner.getValue());
		                g.setArticle1(a);
		                InterfaceClient home=new InterfaceClient();
		                frame.setVisible(false);
		 
		        } catch (RemoteException e2) {
		            e2.printStackTrace();
		        }
		    
			}
		});
		btnModifier.setBounds(308, 389, 130, 39);
		frame.getContentPane().add(btnModifier);
		
		JButton btnChercher = new JButton("Chercher");
		btnChercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rechercher();
			}
		});
		btnChercher.setBounds(542, 129, 115, 30);
		frame.getContentPane().add(btnChercher);
		
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
		String[][] table = null;
		String s=txtRef.getText();
		try {
			 table = ((String[][])g.getArticle(s));
			 
		} catch (RemoteException e) {
			//e.printStackTrace();
		    JOptionPane.showMessageDialog(null,
		             "Aucun article ne correspond à cette reference", "Avertissement",
		             JOptionPane.WARNING_MESSAGE);
		             ModifierArticle ma= new ModifierArticle();
		}
		
		if (table.length == 0) {
		     JOptionPane.showMessageDialog(null,
		     "Aucun article ne correspond à cette reference", "Avertissement",
		     JOptionPane.WARNING_MESSAGE);
		     ModifierArticle ma= new ModifierArticle();} 
		else {
		      
		for(int i=0; i<5;i++) {
			 txtRef.setText(""+table[0][0]);
			 txtFamille.setText(""+table[0][2]);
			 txtPrix.setText(""+table[0][3]);
			 spinner.setValue(Integer.parseInt(table[0][4]));
		}
		}
		
	}
}
