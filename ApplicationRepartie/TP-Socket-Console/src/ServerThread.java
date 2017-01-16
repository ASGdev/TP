import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	private Server mainServ;
	
	public ServerThread(Server serv){
		mainServ = serv;
	}
	
	public void run(){
		
	}	

}
