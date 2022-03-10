package client;

import java.rmi.Naming;
import java.util.List;

import server.Article;
import server.IGestion;

public class Client {
	
	   public static void main(String[] args) {  
	      try { 
	    	  
	    	 IGestion g= (IGestion) Naming.lookup("rmi://localhost:1900/gestion");
	         List<Article> list = (List<Article>)g.getArticles();
	         for (Article s:list) {
	            
	            System.out.println("ref: " + s.getReference());
	            System.out.println("famille: " + s.getFamille());
	            System.out.println("prix: " + s.getPrix());
	            System.out.println("Stock: " + s.getNbStock());
	            System.out.println("ok2");
	            
	         } 
	      } catch (Exception e) {
	         System.err.println("Client exception: " + e.toString());
	         e.printStackTrace();
	      }
}
}
