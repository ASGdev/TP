package BBStep;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {

	public Server() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	}
	
	public void sayhello(){
		System.out.println("Hello");
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		Server refServer = new Server();
		int port = 1099;
		String nom;
		Registry reg = LocateRegistry.createRegistry(port);
		Naming.bind("Server", refServer);
		System.out.println("Server is online and accessible via registry");

	}
	
	

}
