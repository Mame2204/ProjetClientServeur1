package server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import client.Achat;
import client.PaiementCB;
import serverSiege.Facture;
import serverSiege.GestSiegeImpl;
import serverSiege.IGestSiege;

public class GestionImpl extends UnicastRemoteObject implements IGestion {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Connection con;
    private Statement stmt;
    private Statement stmt1;
    private PreparedStatement pstmt;
    private int numUpd;
    private IGestSiege gs;
    
    static  ArrayList<Article> listeArticlesFacture = new ArrayList<Article>();
    static  Facture facture = new Facture();
    
    public static ArrayList<Article> getListeFacturesArticless() {
            return listeArticlesFacture;
        }
    
    
    public GestionImpl() throws RemoteException {
        super(); 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion","root","root");
                stmt=con.createStatement();
                System.out.println("Connected"); 
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[][] getArticles() {
                String[][] data = null; 
                  
                  int rowcount=0;
                  System.out.println("Creating statement..."); 
                  try {
                    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    String sql = "SELECT * FROM article where nbStock >0"; 
                      ResultSet rs = stmt.executeQuery(sql); 
                      rs.last();
                      rowcount = rs.getRow();
                      //Move to beginning
                      rs.beforeFirst();
                      
                      data = new String[rowcount][4];
                      int i=0;
                      while(rs.next())  {
                          for(int j=0;j<4;j++) {
                              data[i][j]=rs.getString(j+1);
                              System.out.println(rs.getString(j+1));
                          }
                          i=i+1;
                      }
                      rs.close();
                      stmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }   
          return data;      
    }

    @Override
    public String[][] getArticle(String r) throws RemoteException {
        String[][] data = null; 
        int rowcount=0; 
        try {
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sq = "SELECT * FROM article where ref='"+r+"' or famille like'%"+r+"%' and nbStock>0";
            
            ResultSet rs = st.executeQuery(sq); 
            rs.last();
            rowcount = rs.getRow();
            //Move to beginning
            rs.beforeFirst();
            
            data = new String[rowcount][4]; 

              int i=0;
              while(rs.next())  {
                  for(int j=0;j<4;j++) {
                      data[i][j]=rs.getString(j+1);
                      System.out.println(rs.getString(j+1));
                  }
                  i=i+1;
              }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        return data;     
    }

    @Override
    public boolean setArticle(Article a) throws RemoteException {
        try {
            stmt = con.createStatement();
            String sql = "UPDATE article set famille='"+a.getFamille()+"',prix="+a.getPrix()+" ,nbStock=" +a.getNbStock()+" where ref='"+a.getReference()+"'";
            
            System.out.println("article: "+a.getFamille()+" "+a.getPrix()+" "+a.getNbStock());
            stmt.executeUpdate(sql);
            stmt.close(); 
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return false;     
    }
    
    @Override
    public boolean setArticle1(Article a) throws RemoteException {
        try {
            stmt = con.createStatement();
            String[][] a1;
            a1=getArticle(a.getReference());
            if (Integer.parseInt(a1[0][3])<a.getNbStock()) {
                JOptionPane.showMessageDialog(null,
                        "Stock insuffisant pour cet article",
                        "Avertissement",
                        JOptionPane.WARNING_MESSAGE);
                
            }else {
                String sql = "UPDATE article set nbStock=nbStock - "+a.getNbStock()+" where ref='"+a.getReference()+"'";
                stmt.executeUpdate(sql);
                stmt.close(); 
                return true;
            }
           
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return false;     
    }
    
    
    @Override
    public String[][] rechArticle(String r) {
        String[][] data = null; 
        int rowcount=0; 
        try {
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sq = "SELECT * FROM article where ref='"+r+"' and nbStock>0";
            
            ResultSet rs = st.executeQuery(sq);  
            rs.last();
            rowcount = rs.getRow();
            
            //Move to beginning
            rs.beforeFirst();
            
            
            data = new String[rowcount][4];

              int i=0;
              while(rs.next())  {
                  for(int j=0;j<4;j++) {
                      data[i][j]=rs.getString(j+1);
                  }
                  i=i+1;
              }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        return data;     
    }
    
    
    @Override
    public boolean createFacture() throws RemoteException {
        try {
            double montant=0.0;
            stmt = con.createStatement();
            String dateDuJour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            for(int i=0; i<Achat.getListeArticles().size();i++) {
                Article a = (Article)Achat.getListeArticles().get(i);
                montant = montant+a.getPrix()*a.getNbStock();
                String s = dateDuJour +", Carte Bancaire, "+a.getReference()+", "+ a.getFamille()+", "+a.getNbStock()+", "+a.getPrix() +", "+a.getPrix()*a.getNbStock() +"\n"; //qte achete et  non stock
                 
            }
            String sql = "INSERT into facture values(0,'"+dateDuJour+"', 'Carte Bancaire',"+montant+")";
            stmt.executeUpdate(sql);
            stmt.close();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return true;     
    }
    
    @Override
    public boolean createFactureArticle() throws RemoteException {
        try {
            stmt1 = con.createStatement();
            for(int i=0; i<Achat.getListeArticles().size();i++) {
                Article a = (Article)Achat.getListeArticles().get(i);
                int ref=getReference(); 
                String sql1 = "INSERT into facture_article values("+ref+",'"+a.getReference()+"',"+a.getNbStock()+")"; 
                stmt1.executeUpdate(sql1);
            }
            stmt1.close();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return true;     
    }

    @Override
    public int getReference() {
        int ref=0;
        try {
            stmt = con.createStatement();
            String sql ="SELECT max(ref) from facture";
              ResultSet rs = stmt.executeQuery(sql); 
                  while(rs.next())  {
                      ref=rs.getInt(1);

                  }
                  rs.close();
                  stmt.close();
              //}
             
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        return ref;   
    }
    
    
    public String[][] TrouveFacture(int refF) {
        String[][] fact = null;
        try {
            fact = ((String[][])gs.getFacture(refF));
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if (fact[0][0] == null) {
            System.out.print("Pas de facture de cette reference");
            JOptionPane.showMessageDialog(null,
                "Pas de facture de cette reference",
                "Avertissement",
                JOptionPane.WARNING_MESSAGE);
        } 
        return fact;
        

    }
    
    
    public String[][] TrouveArticlesFacture(int refF) {
        try {
            gs=new GestSiegeImpl();
            gs= (IGestSiege) Naming.lookup("rmi://localhost:1940/gestion");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        String[][] factureArticle = null;
        String[][] articles=null;
        double mt=0.0;
        
        
        try {
            factureArticle = ((String[][])gs.getFactureArticle(refF));
            System.out.print("taille factureArticle "+factureArticle.length);
            articles = new String[factureArticle.length][5];
            
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        if (factureArticle[0][0] == null) {
            System.out.print("Pas de facture de cette reference");
            JOptionPane.showMessageDialog(null,
                "Pas de facture de cette reference",
                "Avertissement",
                JOptionPane.WARNING_MESSAGE);
        } else {
            for(int i=0; i<factureArticle.length;i++) {
                try {
                    String[][] a = getArticle(factureArticle[i][1]);
                    if (a[0][0] == null) { 
                        System.out.print("Pas de facture de cette reference");
                        JOptionPane.showMessageDialog(null,
                            "Pas de facture de cette reference",
                            "Avertissement",
                            JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        System.out.print("mt "+mt);
                        articles[i][0]=a[0][0];
                        articles[i][1]=a[0][1];
                        articles[i][2]=a[0][2];
                        articles[i][3]=factureArticle[i][2];
                        mt=Double.parseDouble(a[0][2])*Integer.parseInt(factureArticle[i][2]);
                        articles[i][4]=String.valueOf(mt);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
          
                }
                     
                }
            return articles;
         }
    
 

}
