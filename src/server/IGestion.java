package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import serverSiege.Facture;

public interface IGestion extends Remote{

	public String[][] getArticles() throws RemoteException;

	public String[][] getArticle(String r) throws RemoteException;
	
	public boolean setArticle(Article a) throws RemoteException;
	
	public boolean setArticle1(Article a) throws RemoteException;
	
	public String[][] rechArticle(String r) throws RemoteException;
    
    public String[][] TrouveFacture(int refF) throws RemoteException;
    
    public String[][] TrouveArticlesFacture(int refF) throws RemoteException;
    
    public void  MiseAJour() throws RemoteException;

    public boolean createFactureArticle(ArrayList<Article> listeArticles) throws RemoteException;

    public boolean createFacture(ArrayList<Article> listeArticles) throws RemoteException;
	
}
