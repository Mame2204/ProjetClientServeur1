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

import server.GestionImpl;
import server.IGestion;

import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.ScrollPane;

public class InterfaceClient {

	private JFrame frame;
	private JTextField txtSearch;
	private JTable table;
	private JScrollPane scrollPane;
	private IGestion g;

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
		String[] column= {"Réference","Famille","Prix","Stock"};
		String s=txtSearch.getText();
		try {
			table = new javax.swing.JTable((String[][])g.getArticle(s),column);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(234, 107, 718, 100);
		frame.getContentPane().add(scrollPane);
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
			g=new GestionImpl();
			g= (IGestion) Naming.lookup("rmi://localhost:1900/gestion");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 1213, 608);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(310, 5, 571, 39);
		panel.setToolTipText("");
		frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
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
		
		table = new JTable();
		table.setBounds(206, 95, 782, 127);
		String[] column= {"Réference","Famille","Prix","Stock"};
		try {
			table = new javax.swing.JTable((String[][])g.getArticles(),column);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		 
	    scrollPane = new JScrollPane(table);
		scrollPane.setBounds(234, 107, 718, 100);
		frame.getContentPane().add(scrollPane);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component frame = null;
				//Test frame=new Test();
				int reponse=JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quittter ?", "confirm", JOptionPane.YES_NO_OPTION );
				if(reponse==JOptionPane.YES_OPTION) {
					System.exit(0);;
				}
				btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
				btnExit.setBounds(39, 327, 120, 23);
			}
		});
		btnExit.setBounds(369, 313, 115, 29);
		frame.getContentPane().add(btnExit);
		
		JButton btnModifierUnArticle = new JButton("Modifier un article");
		btnModifierUnArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModifierUnArticle.setBounds(638, 313, 185, 29);
		frame.getContentPane().add(btnModifierUnArticle);
		
		
//		frame = new JFrame();
//		frame.setBounds(100, 100, 1213, 608);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
//		
//		JPanel panel = new JPanel();
//		panel.setBounds(310, 5, 571, 39);
//		panel.setToolTipText("");
//		frame.getContentPane().add(panel);
//		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
//		
//		JLabel search = new JLabel("Rechercher:");
//		search.setVerticalAlignment(SwingConstants.TOP);
//		search.setFont(new Font("Tahoma", Font.BOLD, 18));
//		panel.add(search);
//		
//		txtSearch = new JTextField();
//		search.setLabelFor(txtSearch);
//		panel.add(txtSearch);
//		txtSearch.setColumns(25);
//		
//		JButton btnSearch = new JButton("search");
//		btnSearch.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//			}
//		});
//		panel.add(btnSearch);
//		
//		table = new JTable();
//		table.setBounds(206, 95, 782, 127);
//		frame.getContentPane().add(table);
//		
//		JButton btnExit = new JButton("Exit");
//		btnExit.setBounds(369, 313, 115, 29);
//		frame.getContentPane().add(btnExit);
//		
//		JButton btnModifierUnArticle = new JButton("Modifier un article");
//		btnModifierUnArticle.setBounds(638, 313, 185, 29);
//		frame.getContentPane().add(btnModifierUnArticle);
	}
}
