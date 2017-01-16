import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
	int port = 3000;
	String host = "localhost";
	int Threadpool = 10;
	Vector<Socket> waitingConnectionPool;
	static ServerSocket socketserver;
	
	public Server(){
		
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
	
	private void readNotification(Socket soc){
		
	}
	
	private void writeNotification(Socket soc, Notification n){
		
	}
	
	private void readData(Socket soc){
		
	}
	
	private void writeData(Socket soc, String s){
		
	}

	public static void main(String[] zero) {
		Server serv = new Server();
		serv.mainTCP();
	}
		
		
		

}