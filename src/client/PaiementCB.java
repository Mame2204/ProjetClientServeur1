package client;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;

import server.Article;
//import server.GestionImpl;
import server.IGestion;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
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

public class PaiementCB extends Achat{
 
    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private IGestion g;    

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
        try {
            //g=new GestionImpl();
            g= (IGestion) Naming.lookup("rmi://localhost:1940/gestion");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        frame = new JFrame();
        frame.setBounds(100, 100, 976, 584);
        frame.getContentPane().setBackground(Color.decode("#85929E"));
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
        btnPayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String mP="Carte Bancaire";
                try {
                    
                   //int refFacture=g.createFacture(Achat.getListeArticles(), mP);
                    int refLastFact=lireDernierEnregFile()+1;
                    //System.out.println("Ref facture en cours "+refLastFact);
                    g.createFactureArticle(Achat.getListeArticles(), refLastFact);
                    //System.out.print("TEST ref fact "+refFacture);
                    ecrireTicketDeCaisse(refLastFact);
                    InterfaceClient ic=new InterfaceClient();
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                frame.setVisible(false);
            }
        });
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
    
    public void ecrireTicketDeCaisse(int refLastFact) {
        //Path chemin = Paths.get("././Ticket_De_Caisse/Facturation.csv");
        Path chemin = Paths.get("Facturation.csv");
        for(int i=0; i<Achat.getListeArticles().size();i++) {
            Article a = (Article)Achat.getListeArticles().get(i);
                String dateDuJour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                String s = dateDuJour +","+refLastFact+", Carte Bancaire, "+a.getReference()+", "+ a.getFamille()+", "+a.getNbStock()+", "+a.getPrix() +", "+a.getPrix()*a.getNbStock() +"\n";
                try {
                    g.setArticle(a); 
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                byte[] data = s.getBytes();
                OutputStream output = null; 
                try {
                    output = new BufferedOutputStream(Files.newOutputStream(chemin, StandardOpenOption.APPEND)); 
                    output.write(data);
                    output.flush();
                    output.close();
                } 
                catch (Exception e) { System.out.println("Message " + e); }
       }
        Achat.setListeArticles(new ArrayList<Article>());
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




