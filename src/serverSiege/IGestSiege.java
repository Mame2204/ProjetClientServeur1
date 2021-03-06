package serverSiege;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import server.Article;

public interface IGestSiege extends Remote{
    
    public String[][] getFactures(String r) throws RemoteException;
    
    public int getCA(String r) throws RemoteException;
    
    public String[][] getFacture(int r) throws RemoteException;
    
    public String[][] getFactureArticle(int r) throws RemoteException;
    
    public boolean createFactureArticle(ArrayList<Article> listeArticles, int ref) throws RemoteException;
    
    public void createFacture() throws RemoteException;
    
    public int getReference() throws RemoteException;
    
    public boolean MiseAJour() throws RemoteException;

}
