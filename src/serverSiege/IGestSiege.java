package serverSiege;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGestSiege extends Remote{
    public String[][] getFactures(String r) throws RemoteException;
    public int getCA(String r) throws RemoteException;

}
