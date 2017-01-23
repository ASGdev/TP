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
		mainServ.ServerNot("Thread online");
		while(alive){			
			Socket client = mainServ.getConnectionSocket();		
			//Simulation of getting file and print it
			mainServ.ServerNot("Client récupéré");
			try {
				TCP.writeProtocole(client, Notification.REPLY_THREAD_WIP);
				TCP.fileTransfert(
				        client.getInputStream(),
				        new FileOutputStream("Server.txt"),//get ressources, or can forward to ptinting
				        false);
				
				TCP.writeProtocole(client, Notification.REPLY_PRINT_OK);
					
				
				mainServ.ServerNot("Client servi");
				sleep(20000);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
        
	}	
	
	public void killThread(){
		alive = false;
	}
	
	
}
