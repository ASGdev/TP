import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Supplier extends UnicastRemoteObject implements ISupplier {
	private String name;

	protected Supplier(String n) throws RemoteException {		
		super();
		name =n;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String question() throws RemoteException {
		// TODO Auto-generated method stub
		return "Ouai, c'est une question ?";
	}

	@Override
	public String question(String s) throws RemoteException {
		// TODO Auto-generated method stub
		return "Ouai, c'est une bonne question ca... ????";
	}

	@Override
	public String name() throws RemoteException {
		
		return null;
	}

}
