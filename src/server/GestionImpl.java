package server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import serverSiege.GestSiegeImpl;
import serverSiege.IGestSiege;

public class GestionImpl extends UnicastRemoteObject implements IGestion {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Connection con;
    private Statement stmt;
    private IGestSiege gs;

    public GestionImpl() throws RemoteException {
        super(); 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion","root","root");
                stmt=con.createStatement();
                System.out.println("Connected Magasin"); 
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        } catch (ClassNotFoundException e) { 
            e.printStackTrace();
        }
        
        try {
            gs=new GestSiegeImpl();
            gs= (IGestSiege) Naming.lookup("rmi://localhost:1920/gestion");
        } catch (Exception e1) { 
            e1.printStackTrace();
        }
    }

    @Override
    public String[][] getArticles() {
                String[][] data = null; 
                  int rowcount=0;
                  System.out.println("Creating statement..."); 
                  try {
                    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    String sql = "SELECT *  FROM article INNER JOIN famille ON article.ref_famille=famille.ref where nbStock >0"; 
                  
                      ResultSet rs = stmt.executeQuery(sql); 
                      rs.last();
                      rowcount = rs.getRow();
                      rs.beforeFirst();
                      
                      data = new String[rowcount][5]; 
                      int i=0;
                      while(rs.next())  {
                             
                              data[i][0]=rs.getString(1);
                              data[i][1]= rs.getString(7);
                              data[i][2]=rs.getString(2);
                              data[i][3]=rs.getString(3);
                              data[i][4]=rs.getString(4);
                          
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
            String sq = "SELECT * FROM article INNER JOIN famille ON article.ref_famille=famille.ref where article.ref='"+r+"' or famille.nom like '%"+r+"%' or designation like'%"+r+"%' and nbStock>0";
            
            ResultSet rs = st.executeQuery(sq); 
            rs.last();
            rowcount = rs.getRow();
            rs.beforeFirst();
            
            data = new String[rowcount][5]; 

            int i=0;
            while(rs.next())  {
                   
                data[i][0]=rs.getString(1); //reference
                data[i][1]= rs.getString(7); //famille
                data[i][2]=rs.getString(2); //designation
                data[i][3]=rs.getString(3); //prix
                data[i][4]=rs.getString(4); //nbStock
                
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
            String[][] a1;
            a1=getArticle(a.getReference());
            if (Integer.parseInt(a1[0][4])<a.getNbStock()) {
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
    public boolean setArticle1(Article a) throws RemoteException {
        try {
            stmt = con.createStatement();
            String sql = "UPDATE article set nbStock = nbStock +"+a.getNbStock()+" where ref='"+a.getReference()+"'";
            
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
        String[][] data = null; 
        int rowcount=0; 
        try {
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sq = "SELECT * FROM article INNER JOIN famille ON article.ref_famille=famille.ref where article.ref='"+r+"'";
            ResultSet rs = st.executeQuery(sq);  
            rs.last();
            rowcount = rs.getRow();
            rs.beforeFirst();
            
            
            data = new String[rowcount][5];

              int i=0;
              while(rs.next())  {
                     
                  data[i][0]=rs.getString(1); //reference
                  data[i][1]= rs.getString(7); //famille
                  data[i][2]=rs.getString(2); //designation
                  data[i][3]=rs.getString(3); //prix
                  data[i][4]=rs.getString(4); //nbStock
                      
                  i=i+1;
              }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        return data;     
    }
    
    @Override
    public int createFacture(ArrayList<Article> listeArticles, String mP) throws RemoteException {
        int refFacture=gs.createFactureS(listeArticles, mP);
        
        return refFacture;     
    }
    
    @Override
    public boolean createFactureArticle(ArrayList<Article> listeArticles) throws RemoteException {
        gs.createFactureArticleS(listeArticles);
        return true;    
    }

   
    
    
    public String[][] TrouveFacture(int refF) {
        String[][] fact = null;
        try {
            fact = ((String[][])gs.getFacture(refF));
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Pas de facture de cette reference",
                    "Avertissement",
                    JOptionPane.WARNING_MESSAGE);
        }
        
        return fact;
        
    }
    
    
    public String[][] TrouveArticlesFacture(int refF) {
        
        String[][] factureArticle = null;
        String[][] articles=null;
        double mt=0.0;
        try {
            factureArticle = ((String[][])gs.getFactureArticle(refF));
            articles = new String[factureArticle.length][5];
            for(int i=0; i<factureArticle.length;i++) {
                
                articles[i][0]=factureArticle[i][0]; //reference
                articles[i][1]=factureArticle[i][1]; //designation
                articles[i][2]=factureArticle[i][2]; //prix
                articles[i][3]=factureArticle[i][3]; //Quantite
                mt=Double.parseDouble(factureArticle[i][2])*Integer.parseInt(factureArticle[i][3]);
                articles[i][4]=String.valueOf(mt); //Montant
            }
            
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Pas de facture de cette reference",
                    "Avertissement",
                    JOptionPane.WARNING_MESSAGE);
        }
            return articles;}
    
    @Override
    public void MiseAJour() throws RemoteException {
        gs.MiseAJour();

    }
 

}
