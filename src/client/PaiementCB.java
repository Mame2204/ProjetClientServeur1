package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PaiementCB {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaiementCB window = new PaiementCB();
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
	public PaiementCB() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 976, 584);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(440, 16, 499, 496);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nom sur la carte : ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(74, 77, 156, 20);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(74, 113, 363, 40);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNumroDeLa = new JLabel("Num\u00E9ro de la carte :");
		lblNumroDeLa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNumroDeLa.setBounds(74, 179, 195, 30);
		panel.add(lblNumroDeLa);
		
		textField_1 = new JTextField();
		textField_1.setBounds(74, 225, 363, 40);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Date expiration :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(74, 295, 150, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cryptogramme visuel :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(258, 295, 188, 20);
		panel.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(74, 342, 146, 40);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(291, 342, 146, 40);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnPayer = new JButton("Payer");
		btnPayer.setBounds(194, 428, 115, 29);
		panel.add(btnPayer);
		
		JLabel lblPaiementParCb = new JLabel("Paiement par CB");
		lblPaiementParCb.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblPaiementParCb.setBounds(194, 26, 164, 20);
		panel.add(lblPaiementParCb);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 16, 437, 496);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel image = new JLabel("");
		image.setBackground(Color.WHITE);
		Image img= new ImageIcon(this.getClass().getResource("/logoCB.PNG")).getImage();
		image.setIcon(new ImageIcon(img));
		image.setBounds(0, 0, 437, 496);
		panel_1.add(image);
		frame.setVisible(true);
	}
}
