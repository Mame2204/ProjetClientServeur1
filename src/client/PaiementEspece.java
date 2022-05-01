package client;

import java.awt.Color;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PaiementEspece {

	private JFrame frame;
	private IGestion g;
	
	static  ArrayList<Article> listeArticlesFacture = new ArrayList<Article>();
    
    public static ArrayList<Article> getListeArticlesFacture() {
            return listeArticlesFacture;
        }
    
    public static void setListeArticlesFacture(ArrayList<Article> listeArticlesFacture) {
        PaiementCB.listeArticlesFacture = listeArticlesFacture;
    }

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
	    try {
            g=new GestionImpl();
            g= (IGestion) Naming.lookup("rmi://localhost:1940/gestion");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
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
		btnPayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InterfaceClient ic=new InterfaceClient();
                try {
                    g.createFacture();
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                ecrireTicketDeCaisse();
                frame.setVisible(false);
            }
        });
        btnPayer.setBounds(444, 495, 115, 29);
		frame.getContentPane().add(btnPayer);
		frame.setVisible(true);
	}
	
	
	public void ecrireTicketDeCaisse() {
        Path chemin = Paths.get("/Users/mariamekaba/eclipse-workspace/ProjetClientServeur1/Ticket_De_Caisse/Facturation.csv");

	    System.out.print(Achat.getListeArticles().size());
        PaiementEspece.setListeArticlesFacture(Achat.getListeArticles());
        for(int i=0; i<Achat.getListeArticles().size();i++) {
            Article a = (Article)Achat.getListeArticles().get(i);
                String dateDuJour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                String s = dateDuJour +", Carte Bancaire, "+a.getReference()+", "+ a.getFamille()+", "+a.getNbStock()+", "+a.getPrix() +", "+a.getPrix()*a.getNbStock() +"\n"; //qte achete et  non stock
                try {
                    g.setArticle1(a); 
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
