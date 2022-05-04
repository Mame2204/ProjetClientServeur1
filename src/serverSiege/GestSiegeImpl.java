package serverSiege;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import client.Achat;
import server.Article;
import server.GestionImpl;
import server.IGestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestSiegeImpl extends UnicastRemoteObject implements IGestSiege {

    private static final long serialVersionUID = 1L;
    private Connection con;
    private Statement stmt;
    private Statement stmt1;
    private PreparedStatement pstmt;
    private int numUpd;
    
    
    public GestSiegeImpl() throws RemoteException {
        super();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion","root","root");
                stmt=con.createStatement();
                System.out.println("Connected Siege");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    
    @Override
    public String[][] getFactures (String r) {
        String[][] data = null; 
        int rowcount=0;
        
            System.out.println("Creating statement..."); 
            try {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sql = "SELECT * FROM facture where date like '"+r+"%' or ref="+r+"";
                ResultSet rs = stmt.executeQuery(sql);
                rs.last();
                rowcount = rs.getRow();
                //Move to beginning
                rs.beforeFirst();
                
                data = new String[rowcount][4];
                int i=0;
                  while(rs.next()) {
                            try {
                              data[i][0]=rs.getString(1);
                              data[i][1]=rs.getString(2);
                              data[i][2]=rs.getString(3);
                              data[i][3]=rs.getString(4);
                              
                          } catch (SQLException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                          }
                        
                        i=i+1;}
                  rs.close();
                  stmt.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
                
        return data;
    }
    
    @Override
    public String[][] getFactureArticle(int r) {
        String[][] data = null; 
        int rowcount=0;
        
        System.out.println("Creating statement..."); 
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sqll = "SELECT * FROM facture_article where ref_facture = "+r+"";
            ResultSet rs = stmt.executeQuery(sqll);

            rs.last();
            rowcount = rs.getRow();
            
            //Move to beginning
            rs.beforeFirst();
            
            data = new String[rowcount][4];
            int i=0;
            while(rs.next()) {
                      try {
                        data[i][0]=rs.getString(1);
                        data[i][1]=rs.getString(2);
                        data[i][2]=rs.getString(3);
                        
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                  
                  i=i+1;}
              rs.close();
              stmt.close();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
      
    return data;
}
    
    @Override
    public String[][] getFacture (int r) {
        String[][] data = null; 
        int rowcount=0;
        
            System.out.println("Creating statement..."); 
            try {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sql = "SELECT * FROM facture where ref='"+r+"'";
                ResultSet rs = stmt.executeQuery(sql);
                
                rs.last();
                rowcount = rs.getRow();
                
                //Move to beginning
                rs.beforeFirst();
                
                data = new String[rowcount][4];
                int i=0;
                  while(rs.next()) {
                            try {
                              data[i][0]=rs.getString(1);
                              data[i][1]=rs.getString(2);
                              data[i][2]=rs.getString(3);
                              data[i][3]=rs.getString(4);
                              
                          } catch (SQLException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                          }
                        
                        i=i+1;}
                  rs.close();
                  stmt.close();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
                
        return data;
    }
    
    @Override
    public int getCA (String r) {
        
        int CA = 0;
        System.out.println("Creating statement..."); 
        try {
            stmt = con.createStatement();
            String sql = "SELECT sum(montant) FROM facture where date like '"+r+"%'";
            ResultSet rs = stmt.executeQuery(sql);

                        try {
                            while(rs.next())  {
                          CA=rs.getInt(1);
                            } 
                      } catch (SQLException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                      }
              rs.close();
              stmt.close();

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
            
    return CA;
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
             
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        return ref;   
    }
    

    @Override
    public boolean createFactureS(ArrayList<Article> listeArticles) throws RemoteException {
        
        try {
            double montant=0.0;
            String s="";
            
            stmt = con.createStatement();
            String dateDuJour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            
            for(int i=0; i<listeArticles.size();i++) {
                Article a = (Article)listeArticles.get(i);
                montant = montant+a.getPrix()*a.getNbStock();
                s = dateDuJour +", Carte Bancaire, "+a.getReference()+", "+ a.getFamille()+", "+a.getNbStock()+", "+a.getPrix() +", "+a.getPrix()*a.getNbStock() +"\n"; //qte achete et  non stock
                 
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
    public boolean createFactureArticleS(ArrayList<Article> listeArticles) throws RemoteException {
        try {
            stmt1 = con.createStatement();
            for(int i=0; i<listeArticles.size();i++) {
                Article a = (Article)listeArticles.get(i);
                int ref=getReference(); 
                String sql1 = "INSERT into facture_article values("+ref+",'"+a.getReference()+"',"+a.getNbStock()+")"; 
                stmt1.executeUpdate(sql1);
            }
            stmt1.close();
            return true; 
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return false;     
    }
    
    @Override
    public boolean MiseAJour() throws RemoteException {
        try {
            stmt = con.createStatement();
            System.out.print("test");
            String sql = "UPDATE article set prix = prix + 10 ";
            
            stmt.executeUpdate(sql);
            stmt.close(); 
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    
   }

