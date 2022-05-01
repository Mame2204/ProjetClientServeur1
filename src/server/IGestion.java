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

    boolean createFacture() throws RemoteException;
    
    public int getReference() throws RemoteException;
    
    public String[][] TrouveFacture(int refF) throws RemoteException;
    
    public String[][] TrouveArticlesFacture(int refF) throws RemoteException;

    boolean createFactureArticle() throws RemoteException;
	
}
