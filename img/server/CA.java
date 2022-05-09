package server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import javax.swing.*;

public class CA {

    private JFrame frame;
    private JTextField txtDate;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel lblChiffreDaffaires;
    private JLabel valueChiffreDaffaires;
    //private IGestSiege g;
    private IGestion g;

    /**
     * Launch the application.
     */
    public static void main(String[] args) { 
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CA window = new CA();
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
    public CA() {
        initialize();
    }
    
    private void rechercher() {
        table = new JTable(); 
        table.setBounds(30, 40, 794, 127);
        
        String s=txtDate.getText();
        String[] column= {"Reference facture", "Date", "Mode de paiement","Montant"};
        
        try {
            table = new javax.swing.JTable((String[][])g.getFactures(s),column);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            valueChiffreDaffaires.setText(""+g.getCA(s));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(65, 110, 718, 220);
        frame.getContentPane().add(scrollPane);
        
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
        frame.getContentPane().setBackground(Color.decode("#85929E"));
        frame.setBounds(100, 100, 944, 543);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblDate = new JLabel("Date :");
        lblDate.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblDate.setBounds(67, 67, 69, 20);
        frame.getContentPane().add(lblDate);
        
        txtDate = new JTextField();
        txtDate.setBounds(190, 61, 240, 34);
        frame.getContentPane().add(txtDate);
        txtDate.setColumns(10);
        
        table = new JTable();
        table.setBounds(30, 40, 794, 127);
        String[] column= {"Date","Reference facture","Mode de paiement","Montant"};
        table = new javax.swing.JTable(new String[0][4],column);
         
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(65, 110, 718, 220);
        frame.getContentPane().add(scrollPane);
        
        lblChiffreDaffaires = new JLabel("Chiffre d'affaires :");
        lblChiffreDaffaires.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblChiffreDaffaires.setBounds(67, 397, 195, 20);
        frame.getContentPane().add(lblChiffreDaffaires);
        
        valueChiffreDaffaires = new JLabel("");
        valueChiffreDaffaires.setBounds(300, 397, 69, 20);
        frame.getContentPane().add(valueChiffreDaffaires);
        
        JButton btnCharcher = new JButton("Chercher");
        btnCharcher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                rechercher();
            }
        });
        btnCharcher.setBounds(497, 59, 115, 34);
        frame.getContentPane().add(btnCharcher);
        
    }
}
