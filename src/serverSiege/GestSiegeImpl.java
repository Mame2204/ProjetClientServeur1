package serverSiege;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import client.Achat;
import client.Consult_Facture;
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
            String sqll = "SELECT * FROM facture_article INNER JOIN article ON facture_article.ref_article = article.ref where ref_facture = "+r+"";
            ResultSet rs = stmt.executeQuery(sqll);

            rs.last();
            rowcount = rs.getRow();
            rs.beforeFirst();
            
            data = new String[rowcount][4];
            int i=0;
            while(rs.next()) {
                        data[i][0]=rs.getString(2); //reference
                        data[i][1]= rs.getString(5); //designation
                        data[i][2]=rs.getString(6); //prix
                        data[i][3]=rs.getString(3); //nbStock
                  
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
    public void createFacture() throws RemoteException {
        String[][] factures=lireTicketDeCaisse();
        int refDebut=getReference()+1;
        double montant=0.0;
        try {
        
        for(int i=1; i<factures.length;i++) {
            if ( Integer.parseInt(factures[i][1].trim()) == refDebut) {
                int j=i;
                    while((j < factures.length) && (Integer.parseInt(factures[i][1].trim()) == Integer.parseInt(factures[j][1].trim()))) {
                        montant = montant+Double.parseDouble(factures[j][7].trim());
                        j++;
                        
                }
                    i=j-1;
                    stmt = con.createStatement();
                    String sql = "INSERT into facture values("+factures[i][1].trim()+",'"+factures[i][0].trim()+"', '"+factures[i][2].trim()+"' ,"+montant+")";
                    stmt.executeUpdate(sql);
                    stmt.close();
            }
            
        }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
            
    }
    
    public String[][] lireTicketDeCaisse() {
        String[][] facture = null;//new String[9];
        FileReader fr = null;
        int count = 0;
        try
        {
            File file = new File("Facturation.csv");
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()) {
              sc.nextLine();
              count++;
            }
            
            facture=new String[count][8];
            fr = new FileReader(file);
            BufferedReader bfr =new BufferedReader(fr);
            String line = null;
            int row = 0;
            
            
            //read each line of text file
            while((line = bfr.readLine()) != null)
            {
                int col = 0;
                StringTokenizer stk = new StringTokenizer(line,",");
                while (stk.hasMoreTokens())
                {
                    //get next token and store it in the array
                    facture[row][col] = stk.nextToken();
                    col++;
                }
                row++;
            }
            
            //close the file
            bfr.close();
            System.out.print(facture[1]);
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return(facture);
    }
    
    @Override
    public boolean createFactureArticle(ArrayList<Article> listeArticles, int ref) throws RemoteException {
        try {
            stmt1 = con.createStatement();
            //int ref=getReference(); 
            for(int i=0; i<listeArticles.size();i++) {
                Article a = (Article)listeArticles.get(i);
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

