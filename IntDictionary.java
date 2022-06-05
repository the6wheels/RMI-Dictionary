package test2;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IntDictionary extends Remote{
    
    void save(String word, String def) throws RemoteException;
    
    String lookup(String keyword)throws RemoteException;
    
    List<String> list() throws RemoteException ;
    
    void removeWord(String word) throws RemoteException;
    
    void replaceWord(String word, String def) throws RemoteException;
    
	public boolean authenticate(String userName, String password) throws RemoteException;
    
}