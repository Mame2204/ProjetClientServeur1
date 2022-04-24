package client;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

public class Paiement {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Paiement window = new Paiement();
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
	public Paiement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 945, 417);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		ButtonGroup bg = new ButtonGroup();
		
		JLabel lblchoixPaiement = new JLabel("Veuillez choisir votre moyen de paiement");
		lblchoixPaiement.setFont(new Font("Sitka Display", Font.BOLD, 22));
		lblchoixPaiement.setBounds(243, 16, 406, 20);
		frame.getContentPane().add(lblchoixPaiement);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setForeground(Color.GREEN);
		progressBar.setValue(5);
		progressBar.setBounds(209, 255, 406, 14);
		progressBar.setVisible(false);
		frame.getContentPane().add(progressBar);
		
		JLabel lblPrparationDuPaiement = new JLabel("Pr\u00E9paration du paiement...");
		lblPrparationDuPaiement.setBounds(262, 219, 266, 20);
		lblPrparationDuPaiement.setVisible(false);
		frame.getContentPane().add(lblPrparationDuPaiement);
		
		JRadioButton rdbtEspece = new JRadioButton("Esp\u00E8ces");
		rdbtEspece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblPrparationDuPaiement.setVisible(true);
				progressBar.setVisible(true);
			}
		});
		rdbtEspece.setBounds(44, 117, 155, 29);
		frame.getContentPane().add(rdbtEspece);
		
		JRadioButton rdbtCB = new JRadioButton("Carte Bancaire");
		rdbtCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPrparationDuPaiement.setVisible(true);
				progressBar.setVisible(true);
			}
		});
		rdbtCB.setBounds(361, 117, 155, 29);
		frame.getContentPane().add(rdbtCB);
		
		JRadioButton rdbtCheque = new JRadioButton("Ch\u00E8que");
		rdbtCheque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPrparationDuPaiement.setVisible(true);
				progressBar.setVisible(true);
			}
		});
		rdbtCheque.setBounds(678, 117, 155, 29);
		frame.getContentPane().add(rdbtCheque);
		
		bg.add(rdbtCB);
		bg.add(rdbtEspece);
		bg.add(rdbtCheque);
		
		frame.setVisible(true);
		
	}
}
