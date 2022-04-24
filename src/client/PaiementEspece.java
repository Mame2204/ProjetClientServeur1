package client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PaiementEspece {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaiementEspece window = new PaiementEspece();
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
	public PaiementEspece() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1014, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPaiementEnEspce = new JLabel("Paiement en esp\u00E8ces");
		lblPaiementEnEspce.setForeground(Color.BLUE);
		lblPaiementEnEspce.setBackground(Color.WHITE);
		lblPaiementEnEspce.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPaiementEnEspce.setBounds(383, 16, 215, 20);
		frame.getContentPane().add(lblPaiementEnEspce);
		
		JLabel image = new JLabel("");
		image.setBackground(Color.WHITE);
		Image img= new ImageIcon(this.getClass().getResource("/especes.jpg")).getImage();
		image.setIcon(new ImageIcon(img));
		image.setBounds(0, 60, 1057, 424);
		frame.getContentPane().add(image);
		
		JButton btnPayer = new JButton("Payer");
		btnPayer.setBounds(444, 495, 115, 29);
		frame.getContentPane().add(btnPayer);
		frame.setVisible(true);
	}

}
