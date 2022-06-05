package test2;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server{



        
    public static void main(String[] argv) throws RemoteException{
         


        try {

            String name = "Words";
            IntDictionary engine = new FaactoryImp();
            IntDictionary stub = (IntDictionary) UnicastRemoteObject.exportObject(engine, 0);
            
            
            Registry registry = LocateRegistry.createRegistry(1888);
            registry.rebind(name, stub);
            System.out.println("Connection Successful");
            
        } catch (Exception e) {
        	
            System.err.println("ERROR connecting:");
            e.printStackTrace();
        }
         
     }

}