import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	private Server mainServ;
	private ServerSocket serversocket;
	boolean alive;
	
	public ServerThread(Server serv){
		mainServ = serv;
		serversocket = mainServ.getServerSocket();
		
	}
	
	public void run(){
		alive = true;
		while(alive){
			mainServ.ServerNot("Thread online");
			Socket client = mainServ.getConnectionSocket();		
			//Simulation of getting file and print it
			mainServ.ServerNot("Client récupéré");
			try {
				TCP.writeProtocole(client, Notification.REPLY_THREAD_WIP);
				TCP.fileTransfert(
				        client.getInputStream(),
				        new FileOutputStream("Server.txt"),//get ressources, or can forward to ptinting
				        false);
				while(client.isConnected()){
					TCP.writeProtocole(client, Notification.REPLY_PRINT_OK);
				}				
				mainServ.ServerNot("Client servi");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				wait(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //spooler waiting time simulation
		}	
        
	}	
	
	public void killThread(){
		alive = false;
	}
	
	
}
