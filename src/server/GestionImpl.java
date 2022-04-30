package server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import client.Achat;
import client.PaiementCB;

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
                  String[][] data = new String[3][4]; // [rows][columns]        
                  System.out.println("Creating statement..."); 
                  try {
                    stmt = con.createStatement();
                    String sql = "SELECT * FROM article where nbStock >0"; 
                      ResultSet rs = stmt.executeQuery(sql);  
                      System.out.println("des articles");
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
    public String[][] getArticle(String r) {
        String[][] data = new String[3][4]; // [rows][columns]  
        try {
            
            Statement st = con.createStatement();
            String sq = "SELECT * FROM article where ref='"+r+"' or famille like'%"+r+"%' and nbStock>0";
            System.out.println("article");
            ResultSet rs = st.executeQuery(sq);  

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
            //String sql = "UPDATE article set famille='"+a.getFamille()+"',prix="+a.getPrix()+" ,nbStock=" +a.getNbStock()+" where ref='"+a.getReference()+"'";
            String sql = "UPDATE article set nbStock=nbStock - "+a.getNbStock()+" where ref='"+a.getReference()+"'";
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
    public String[][] rechArticle(String r) {
        String[][] data = new String[1][4]; // [rows][columns]  
        try {
            
            Statement st = con.createStatement();
            String sq = "SELECT * FROM article where ref='"+r+"' and nbStock>0";
            System.out.println("article");
            ResultSet rs = st.executeQuery(sq);  

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
    public boolean createFacture() throws RemoteException {
        try {
            double montant=0.0;
            stmt = con.createStatement();
            String dateDuJour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            //Statement stmt1 = con.createStatement();
            for(int i=0; i<Achat.getListeArticles().size();i++) {
                System.out.print("New Liste"+Achat.getListeArticles().size());
                Article a = (Article)Achat.getListeArticles().get(i);
                montant = montant+a.getPrix()*a.getNbStock();
                String s = dateDuJour +", Carte Bancaire, "+a.getReference()+", "+ a.getFamille()+", "+a.getNbStock()+", "+a.getPrix() +", "+a.getPrix()*a.getNbStock() +"\n"; //qte achete et  non stock
                
                //String recupRefFact="SELECT max(ref) from facture";
                //stmt.executeQuery(recupRefFact);
                
                /*
                 * int ref=getReference(); String sql1 =
                 * "INSERT into facture_article values("+ref+",'"+a.getReference()+"',"+a.
                 * getNbStock()+")"; stmt.executeUpdate(sql1);
                 */
                 
            }
            String sql = "INSERT into facture values(0,'"+dateDuJour+"', 'Carte Bancaire',"+montant+")";
            stmt.executeUpdate(sql);
            stmt.close(); 
            //stmt1.close(); 
            //return true;
            
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
            //Statement stmt1 = con.createStatement();
            for(int i=0; i<Achat.getListeArticles().size();i++) {
                System.out.print("New Liste"+Achat.getListeArticles().size());
                Article a = (Article)Achat.getListeArticles().get(i);
                int ref=getReference(); 
                String sql1 = "INSERT into facture_article values("+ref+",'"+a.getReference()+"',"+a.getNbStock()+")"; 
                stmt1.executeUpdate(sql1);
            }
            stmt1.close(); 
            //stmt1.close(); 
            //return true;
            
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
              System.out.println("des articles");
              int i=0;
              while(rs.next())  {
                      ref=rs.getInt(1);
                      System.out.println(rs.getInt(1));

              }
              rs.close();
              stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        return ref;   
    }
    
    /*
     * @Override public boolean setArticle(Article a) throws RemoteException { try {
     * stmt = con.createStatement(); String sql =
     * "UPDATE article set famille='"+a.getFamille()+"',prix="+a.getPrix()
     * +" ,nbStock=" +a.getNbStock()+" where ref='"+a.getReference()+"'";
     * System.out.println("article: "+a.getFamille()+" "+a.getPrix()+" "+a.
     * getNbStock()); stmt.executeUpdate(sql); stmt.close(); return true; } catch
     * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); }
     * return false; }
     */

}
