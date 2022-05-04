package server;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class Server{

	public static void main(String args[]) { 
		 try { 
			 
	    	  IGestion g= (IGestion)new GestionImpl();
	    	  LocateRegistry.createRegistry(1940); 
	    	  
	    	  Naming.rebind("rmi://localhost:1940/gestion", g);
	    	  System.err.println("Server ready"); 
	    	  
	      } catch (Exception e) { 
	         System.err.println("Server exception: " + e.toString()); 
	         e.printStackTrace(); 
	      }
	   
	   }

}
