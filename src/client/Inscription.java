package client;

import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;

public class Inscription {

	private JFrame frame;
	private JTextField txtNom;
	private JTextField txtPrenom;
	private JTextField txtPseudo;
	private JTextField txtMdp;
	private JTextField txtEmail;
	private JTextField txtTel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inscription window = new Inscription();
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
	public Inscription() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 797, 367);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblInscription = new JLabel("Inscription");
		lblInscription.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblInscription.setBounds(320, 16, 169, 20);
		frame.getContentPane().add(lblInscription);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(68, 97, 69, 20);
		frame.getContentPane().add(lblNom);
		
		JLabel lblPrnom = new JLabel("Pr\u00E9nom");
		lblPrnom.setBounds(475, 97, 69, 20);
		frame.getContentPane().add(lblPrnom);
		
		JLabel lblNewLabel = new JLabel("Pseudo");
		lblNewLabel.setBounds(68, 153, 91, 20);
		frame.getContentPane().add(lblNewLabel);
		
		txtNom = new JTextField();
		txtNom.setBounds(144, 85, 175, 35);
		frame.getContentPane().add(txtNom);
		txtNom.setColumns(10);
		
		txtPrenom = new JTextField();
		txtPrenom.setBounds(587, 85, 158, 35);
		frame.getContentPane().add(txtPrenom);
		txtPrenom.setColumns(10);
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(144, 141, 175, 35);
		frame.getContentPane().add(txtPseudo);
		txtPseudo.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(68, 211, 69, 20);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblNewLabel_1 = new JLabel("T\u00E9l\u00E9phone");
		lblNewLabel_1.setBounds(475, 211, 82, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setBounds(475, 153, 103, 20);
		frame.getContentPane().add(lblMotDePasse);
		
		txtMdp = new JTextField();
		txtMdp.setBounds(587, 141, 158, 35);
		frame.getContentPane().add(txtMdp);
		txtMdp.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(144, 199, 175, 35);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		txtTel = new JTextField();
		txtTel.setBounds(587, 199, 158, 35);
		frame.getContentPane().add(txtTel);
		txtTel.setColumns(10);
		
		JButton btnSinscrire = new JButton("S'inscrire");
		btnSinscrire.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSinscrire.setBounds(338, 254, 115, 41);
		btnSinscrire.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String firstName = txtNom.getText();
                String lastName = txtPrenom.getText();
                String emailId = txtEmail.getText();
                String userName = txtPseudo.getText();
                String mobileNumber = txtTel.getText();
                int len = mobileNumber.length();
                String password = txtMdp.getText();
                
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion", "root", "");

                    String query = "INSERT INTO client values('"+"0" + "','" + firstName + "','" + lastName + "','" + userName + "','" +
                        password + "','" + emailId + "','" + mobileNumber + "')";

                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x == 0) {
                        JOptionPane.showMessageDialog(btnSinscrire, "This is alredy exist");
                    } else {
                        JOptionPane.showMessageDialog(btnSinscrire, "Welcome, "  + "Your account is sucessfully created");
                        Connexion c=new Connexion();
                    }
                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
				
			}
        });

		frame.getContentPane().add(btnSinscrire);
	}
}
