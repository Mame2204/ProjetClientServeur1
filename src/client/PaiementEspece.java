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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PaiementEspece {

	private JFrame frame;
	private IGestion g;
	

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
            //g=new GestionImpl();
            g= (IGestion) Naming.lookup("rmi://localhost:1940/gestion");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#85929E"));
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
                String mP="Espece";
                try {
                  //int refFacture=g.createFacture(Achat.getListeArticles(), mP);
                    int refLastFact=lireDernierEnregFile()+1;
                    //System.out.println("Ref facture en cours "+refLastFact);
                    g.createFactureArticle(Achat.getListeArticles(), refLastFact);
                    //System.out.print("TEST ref fact "+refFacture);
                    ecrireTicketDeCaisse(refLastFact);
                    //InterfaceClient ic=new InterfaceClient();
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                frame.setVisible(false);
            }
        });
        btnPayer.setBounds(444, 495, 115, 29);
		frame.getContentPane().add(btnPayer);
		frame.setVisible(true);
	}
	
	
	public void ecrireTicketDeCaisse(int refFacture) {
        Path chemin = Paths.get("././Ticket_De_Caisse/Facturation.csv");
        for(int i=0; i<Achat.getListeArticles().size();i++) {
            Article a = (Article)Achat.getListeArticles().get(i);
                String dateDuJour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                String s = dateDuJour +","+refFacture+", Espece, "+a.getReference()+", "+ a.getFamille()+", "+a.getNbStock()+", "+a.getPrix() +", "+a.getPrix()*a.getNbStock() +"\n"; 
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
	
	public int lireDernierEnregFile() {
        String[] facture = new String[9];
        FileReader fr = null;
        try
        {
            //PrintStream l_out = new PrintStream(new FileOutputStream("exemple.csv",true));
            File file = new File("Facturation.csv");
            fr = new FileReader(file);
            BufferedReader bfr =new BufferedReader(fr);
            String line = null;
            //int row = 0;
            
            
            //read each line of text file
            while((line = bfr.readLine()) != null)
            {
                int col = 0;
                StringTokenizer stk = new StringTokenizer(line,",");
                while (stk.hasMoreTokens())
                {
                    //get next token and store it in the array
                    facture[col] = stk.nextToken();
                    col++;
                }
                //row++;
            }
            
            //close the file
            bfr.close();
            System.out.print(facture[1]);
            return Integer.parseInt(facture[1]);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
