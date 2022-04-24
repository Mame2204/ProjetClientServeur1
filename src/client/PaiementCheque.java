package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PaiementCheque {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaiementCheque window = new PaiementCheque();
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
	public PaiementCheque() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 984, 639);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPaiementParChque = new JLabel("Paiement par ch\u00E8que");
		lblPaiementParChque.setForeground(Color.BLUE);
		lblPaiementParChque.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPaiementParChque.setBounds(407, 16, 241, 20);
		frame.getContentPane().add(lblPaiementParChque);
		
		JLabel image = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("/cheque.jpg")).getImage();
		image.setIcon(new ImageIcon(img));
		image.setBounds(0, 52, 962, 470);
		frame.getContentPane().add(image);
		
		JButton btnPayer = new JButton("Payer");
		btnPayer.setBounds(430, 538, 115, 29);
		frame.getContentPane().add(btnPayer);
		frame.setVisible(true);
	}

}
