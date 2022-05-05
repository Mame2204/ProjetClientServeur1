package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import server.Article;
import server.GestionImpl;
import server.IGestion;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PaiementCheque {

	private JFrame frame;
	private IGestion g;

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
	    try {
            //g=new GestionImpl();
            g= (IGestion) Naming.lookup("rmi://localhost:1940/gestion");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#85929E"));
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
		btnPayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String mP="Cheque";
                try {
                    int refFacture=g.createFacture(Achat.getListeArticles(), mP);
                    g.createFactureArticle(Achat.getListeArticles());
                    ecrireTicketDeCaisse(refFacture);
                    InterfaceClient ic=new InterfaceClient();
                    
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                frame.setVisible(false);
            }
        }); 
		btnPayer.setBounds(430, 538, 115, 29);
        frame.getContentPane().add(btnPayer);
        frame.setVisible(true);
	}
	
	public void ecrireTicketDeCaisse(int refFacture) {
        Path chemin = Paths.get("././Ticket_De_Caisse/Facturation.csv");
        for(int i=0; i<Achat.getListeArticles().size();i++) {
            Article a = (Article)Achat.getListeArticles().get(i);
                String dateDuJour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                String s = dateDuJour +","+refFacture+", Carte Cheque, "+a.getReference()+", "+ a.getFamille()+", "+a.getNbStock()+", "+a.getPrix() +", "+a.getPrix()*a.getNbStock() +"\n"; 
                try {
                    g.setArticle(a); 
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                byte[] data = s.getBytes();
                OutputStream output = null; 
                try { 
                    output = new BufferedOutputStream(Files.newOutputStream(chemin, StandardOpenOption.APPEND)); 
                    output.write(data);
                    output.flush();
                    output.close();
                } catch (Exception e) { System.out.println("Message " + e); }
       }
        Achat.setListeArticles(new ArrayList<Article>() );

    }

}
