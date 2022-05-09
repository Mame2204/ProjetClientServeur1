package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.table.DefaultTableModel;

import server.CA;
import server.GestionImpl;
import server.IGestion;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
 
public class InterfaceClient {

	private JFrame frame;
	private JTextField txtSearch;
	JTable table;
	private JScrollPane scrollPane;
	private IGestion g;
	JLabel bienvenue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceClient window = new InterfaceClient();
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
	public InterfaceClient() {
		initialize();
	}
	
	
	private void rechercher() {
		table = new JTable();
		table.setBounds(206, 95, 782, 127);
		String[] column= {"R\u00E9ference" ,"Famille","Description","Prix","Stock"};
		String s=txtSearch.getText();
		try {
			table = new javax.swing.JTable((String[][])g.getArticle(s),column);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(240, 164, 718, 350);
		frame.getContentPane().add(scrollPane);
		
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
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.setBounds(100, 100, 1213, 608);
		frame.getContentPane().setBackground(Color.decode("#85929E")); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(232, 93, 744, 39);
		panel.setToolTipText("");
		frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		bienvenue = new JLabel("Bienvenue");
		bienvenue.setBounds(10, 63, 69, 20);
        
		frame.getContentPane().add(bienvenue);
		
		JLabel search = new JLabel("Rechercher:");
		search.setVerticalAlignment(SwingConstants.TOP);
		search.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(search);
		
		txtSearch = new JTextField();
		search.setLabelFor(txtSearch);
		panel.add(txtSearch);
		txtSearch.setColumns(25);
		
		JButton btnSearch = new JButton("search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rechercher();
			}
			
		});
		panel.add(btnSearch);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table = new JTable();
				txtSearch.setText("");
				table.setBounds(206, 95, 782, 127);
				String[] column= {"R\u00E9ference" ,"Famille","Description","Prix","Stock"};
				try {
					table = new javax.swing.JTable((String[][])g.getArticles(),column);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(240, 164, 718, 350);
				frame.getContentPane().add(scrollPane);
			}
		});
		panel.add(btnReset);
		
		table = new JTable();
		table.setBounds(206, 95, 782, 127);
		String[] column= {"R\u00E9ference" ,"Famille","Description","Prix","Stock"};
		try {
			table = new javax.swing.JTable((String[][])g.getArticles(),column);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		 
	    scrollPane = new JScrollPane(table);
		scrollPane.setBounds(240, 164, 718, 350);
		frame.getContentPane().add(scrollPane);
		
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
        
        JMenuItem mntmCa = new JMenuItem("Chiffre d'affaire");
        mnStock.add(mntmCa);
        mntmCa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                InterfaceCA ca=new InterfaceCA();
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
        
        JMenuItem mntmChargementFacture = new JMenuItem("Chargement facture");
        mnFacture.add(mntmChargementFacture);
        mntmChargementFacture.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    g.createFacture();
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
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
}
