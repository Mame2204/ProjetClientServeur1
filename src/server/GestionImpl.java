package server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionImpl extends UnicastRemoteObject implements IGestion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private int numUpd;
	
	public GestionImpl() throws RemoteException {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion","root","");
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
		String[][] data = new String[2][4]; // [rows][columns]  
		try {
			
			Statement st = con.createStatement();
			String sq = "SELECT * FROM article where ref='"+r+"' or famille like'"+r+"%' and nbStock >0";
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
	      //rs.close(); 
		return data;     
	}

	@Override
	public boolean setArticle(Article a) throws RemoteException {
		try {
			pstmt=con.prepareStatement("UPDATE article set famille='"+a.getFamille()+"',prix="+a.getPrix()+" ,nbStock=" +a.getNbStock()+" where ref='"+a.getReference()+"'+");
			numUpd = pstmt.executeUpdate();
			System.out.println("article: "+a.getFamille()+" "+a.getPrix()+" "+a.getNbStock());
			numUpd = pstmt.executeUpdate();
			pstmt.close(); 
		    return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	      //rs.close(); 
		return false;     
	}

}
