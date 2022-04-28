package serverSiege;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerSiege {
    
    public static void main(String args[]) { 
        try { 
            
             IGestSiege g= (IGestSiege)new GestSiegeImpl();
             LocateRegistry.createRegistry(1920); 
             
             Naming.rebind("rmi://localhost:1920/gestion", g);
             System.err.println("Server ready"); 
             
         } catch (Exception e) { 
            System.err.println("Server exception: " + e.toString()); 
            e.printStackTrace(); 
         }
      
      }

}
