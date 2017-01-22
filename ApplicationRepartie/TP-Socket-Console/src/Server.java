import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server {
	int port = 3000;
	String host = "localhost";
	int Threadpool = 10;
	BlockingQueue<Socket> waitingConnectionPool;
	static ServerSocket socketserver;
	
	public Server(){
		waitingConnectionPool = new ArrayBlockingQueue<>(Threadpool*2);
	}
	
	private void mainTCP(){
		Socket socket ;
		
		try {
			socketserver = new ServerSocket(port);
			System.out.println("Serveur demarre");
			socket = socketserver.accept(); 
			System.out.println("Un client s'est connect� !");
			Notification not = TCP.readProtocole(socket);
			if(not == Notification.QUERY_PRINT){
				
				TCP.writeProtocole(socket, Notification.REPLY_PRINT_OK);
			}else{
				System.out.println("Une erreur s'est produite : le client est bourr�");
			}
		    socketserver.close();
		    socket.close();

		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Socket getConnectionSocket() throws InterruptedException{
		return waitingConnectionPool.take();
	}
	
	private void setConnectionSocket(Socket soc) throws InterruptedException{
		waitingConnectionPool.put(soc);
	}

	public static void main(String[] zero) {
		Server serv = new Server();
		serv.mainTCP();
	}
		
		
		

}