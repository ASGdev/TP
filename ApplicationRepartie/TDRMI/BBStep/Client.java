package BBStep;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	
	public Client(){
		
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		int port = 1099;
		Registry registry = LocateRegistry.getRegistry(port);
		System.out.println(registry.list()[0]);
		IServer refServer = (IServer) registry.lookup("Server");
	
		refServer.sayhello();

	}

}
