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
	
	private void readNotification(Socket soc){
		
	}
	
	private void writeNotification(Socket soc, Notification n){
		
	}
	
	private void readData(Socket soc){
		
	}
	
	private void writeData(Socket soc, String s){
		
	}

}
