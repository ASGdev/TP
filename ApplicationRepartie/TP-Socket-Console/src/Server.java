import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server {
	int port = 3000;
	String host = "localhost";
	int ThreadpoolMaxSize = 1;
	Vector<ServerThread> ThreadPool;
	BlockingQueue<Socket> waitingConnectionPool;
	static ServerSocket socketserver;
	static boolean alive;
	
	public Server(int nbth){
		ThreadpoolMaxSize=nbth;
		waitingConnectionPool = new ArrayBlockingQueue<>(ThreadpoolMaxSize*2);
		ThreadPool = new Vector<ServerThread>();
		for (int i=0;i<ThreadpoolMaxSize;i++){
			ServerThread th = new ServerThread(this);
			th.start();
			ThreadPool.addElement(th);
		}
	}
	
	private void mainTCP(){
		try {
			socketserver = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Serveur demarre");
		alive = true;
		while(alive){
			try {
				Socket socket ;
				socket = socketserver.accept(); 
				System.out.println("Un client s'est connecté !");
				Notification not = TCP.readProtocole(socket);
				if(not == Notification.QUERY_PRINT){
					setConnectionSocket(socket);
					//TCP.writeProtocole(socket, Notification.REPLY_PRINT_OK);
				}else{
					System.out.println("Une erreur s'est produite : le client est bourré");
				}
	
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			socketserver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Socket getConnectionSocket(){
		Socket sock=null;
			try {
				sock = waitingConnectionPool.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return sock;
	}
	
	public void setConnectionSocket(Socket soc){
		try {
			waitingConnectionPool.put(soc);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void downServer(){
		alive=false;
	}
	
	public ServerSocket getServerSocket(){
		return socketserver;
	}
	
	public void ServerNot(String s){
		System.out.println(s);
	}

	public static void main(String[] zero) {
		Server serv = new Server(1);
		serv.mainTCP();
	}
		
		
		

}