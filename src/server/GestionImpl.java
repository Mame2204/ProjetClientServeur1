package server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class GestionImpl extends UnicastRemoteObject implements IGestion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected GestionImpl() throws RemoteException {
		super();
	}

	@Override
	public List<Article> getArticles() {
		
	      List<Article> list = new ArrayList<Article>();   
	      
	      try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	             Connection con;
	            try {
	                con = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion","root","root");
	                Statement stmt=con.createStatement();
	                  System.out.println("Connected"); 
			    
			      System.out.println("Creating statement..."); 
			      
			      stmt = con.createStatement();  
			      String sql = "SELECT * FROM article"; 
			      ResultSet rs = stmt.executeQuery(sql);  

			      while(rs.next()) {  
			        String reference =rs.getString("ref");
			     	String famille=rs.getString("famille");
			    	double prix=rs.getDouble("prix");
			    	int nbStock=rs.getInt("nbStock");
			         
			         Article article = new Article(); 
			         article.setReference(reference);
			         article.setFamille(famille);
			         article.setPrix(prix);
			         article.setNbStock(nbStock);
			         list.add(article); 
			      } 
			      rs.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
	                    
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
         
	      return list;     
	   
	}

}
