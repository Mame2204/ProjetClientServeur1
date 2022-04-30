package serverSiege;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GestSiegeImpl extends UnicastRemoteObject implements IGestSiege {

    private static final long serialVersionUID = 1L;
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private int numUpd;

    public GestSiegeImpl() throws RemoteException {
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
    public String[][] getFactures (String r) {
        
            String[][] data = new String[27][4]; // [rows][columns]
            System.out.println("Creating statement..."); 
            try {
                stmt = con.createStatement();
                String sql = "SELECT * FROM facture where date like '"+r+"%'";
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("des factures");
                int i=0;
                  while(rs.next()) {
                            try {
                              data[i][0]=rs.getString(1);
                              System.out.println(rs.getString(1));
                              data[i][1]=rs.getString(2);
                              System.out.println(rs.getString(2));
                              data[i][2]=rs.getString(3);
                              System.out.println(rs.getString(3));
                              data[i][3]=rs.getString(4);
                              System.out.println(rs.getDouble(4));
                              
                          } catch (SQLException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                          }
                        
                        i=i+1;}
                  rs.close();
                  stmt.close();
                  System.out.println("i: "+i);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
                
        return data;
    }
    
    public int getCA (String r) {
        
        int CA = 0; // [rows][columns]
        System.out.println("Creating statement..."); 
        try {
            stmt = con.createStatement();
            String sql = "SELECT sum(montant) FROM facture where date like '"+r+"%'";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("CA");
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
              System.out.println("data: "+CA);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
            
    return CA;
}
    
   }

