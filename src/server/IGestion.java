package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGestion extends Remote{

	public String[][] getArticles() throws RemoteException;

	public String[][] getArticle(String r) throws RemoteException;
	
	public boolean setArticle(Article a) throws RemoteException;
	
	public boolean setArticle1(Article a) throws RemoteException;
	
	public String[][] rechArticle(String r) throws RemoteException;

    boolean createFacture(Facture F) throws RemoteException;
	
}
