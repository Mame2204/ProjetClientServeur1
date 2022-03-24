package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class Connexion {

	private JFrame frame;
	private JTextField txtPseudo;
	private JTextField txtMdp;
	private UnClient cli;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connexion window = new Connexion();
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
	public Connexion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 829, 510);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblConnexion = new JLabel("Connexion");
		lblConnexion.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblConnexion.setBounds(157, 55, 146, 20);
		frame.getContentPane().add(lblConnexion);
		
		JLabel lblPseudo = new JLabel("Pseudo");
		lblPseudo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPseudo.setBounds(44, 134, 69, 20);
		frame.getContentPane().add(lblPseudo);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMotDePasse.setBounds(44, 206, 126, 20);
		frame.getContentPane().add(lblMotDePasse);
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(280, 119, 219, 40);
		frame.getContentPane().add(txtPseudo);
		txtPseudo.setColumns(10);
		
		txtMdp = new JTextField();
		txtMdp.setBounds(280, 191, 219, 40);
		frame.getContentPane().add(txtMdp);
		txtMdp.setColumns(10);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.setBounds(157, 313, 115, 29);
		btnConnexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 String userName = txtPseudo.getText();
	                String password = txtMdp.getText();
	                try {
	                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion", "root", "");

	                    PreparedStatement st = (PreparedStatement) connection
	                        .prepareStatement("Select id, pseudo, mdp from client where pseudo=? and mdp=?");

	                    st.setString(1, userName);
	                    st.setString(2, password);
	                    ResultSet rs = st.executeQuery();
	                    if (rs.next()) {
	                    	cli=new UnClient();
	                    	cli.setId(rs.getInt(1));
	                    	cli.setPseudo(userName);
	                    	cli.setMdp(password);
	                    	InterfaceClient ic = new InterfaceClient(cli);
	                    	frame.dispose();
	                        JOptionPane.showMessageDialog(btnConnexion, "You have successfully logged in");
	                    } else {
	                        JOptionPane.showMessageDialog(btnConnexion, "Wrong Username & Password");
	                    }
	                } catch (SQLException sqlException) {
	                    sqlException.printStackTrace();
	                }
				
			}
        });

		frame.getContentPane().add(btnConnexion);
		frame.setVisible(true);
	}
}
