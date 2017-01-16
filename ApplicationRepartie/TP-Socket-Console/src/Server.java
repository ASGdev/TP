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
			socket = socketserver.accept(); 
			System.out.println("Un client s'est connecté !");
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