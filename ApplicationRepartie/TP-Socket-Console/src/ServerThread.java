import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	private Server mainServ;
	private ServerSocket serversocket;
	
	public ServerThread(Server serv){
		mainServ = serv;
		serversocket = mainServ.getServerSocket();
		
	}
	
	public void run(){
		mainServ.ServerNot("Thread online");
		Socket client = mainServ.getConnectionSocket();		
		//Simulation of getting file and print it
		try {
			TCP.writeProtocole(client, Notification.REPLY_THREAD_WIP);
			TCP.fileTransfert(
			        client.getInputStream(),
			        new FileOutputStream("Server.txt"),//get ressources, or can forward to ptinting
			        true);
			TCP.writeProtocole(client, Notification.REPLY_PRINT_OK);
			client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}	
	
	
}
