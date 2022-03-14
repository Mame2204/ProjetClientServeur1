package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGestion extends Remote{

	public String[][] getArticles() throws RemoteException;

	public String[][] getArticle(String r) throws RemoteException;
}
