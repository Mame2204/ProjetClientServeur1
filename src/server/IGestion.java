package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface IGestion extends Remote{

	public List<Article> getArticles() throws RemoteException;
}
