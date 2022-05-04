package client;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import server.IGestion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Client extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable jt1;

	public static void main(String[] args) {  
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client c=new Client();
					JFrame frame = new JFrame();
					frame.setTitle("Interface client");
					frame.add(c.getFenetre());
					frame.setSize(1000, 1000);
					frame.setVisible(true);
					//System.out.println("affichage");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JPanel getFenetre() {
	
		IGestion g = null;
		try {
			g= (IGestion) Naming.lookup("rmi://localhost:1910/gestion");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			
		String[] column= {"R�ference","Famille","Prix","Stock"};

		try {
			jt1 = new javax.swing.JTable((String[][])g.getArticles(),column);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JScrollPane scroll = new JScrollPane(jt1);
		contentPane.add(scroll);
		
		JButton btExit=new JButton("Exit");
		btExit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Component frame = null;
					//Test frame=new Test();
					int reponse=JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter ?", "confirm", JOptionPane.YES_NO_OPTION );
					if(reponse==JOptionPane.YES_OPTION) {
						System.exit(0);;
					}
					btExit.setFont(new Font("Tahoma", Font.BOLD, 12));
					btExit.setBounds(39, 327, 120, 23);
				}
			});
		contentPane.add(btExit);
		return contentPane;

		
	}

}
