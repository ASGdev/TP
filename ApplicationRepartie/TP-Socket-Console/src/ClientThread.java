import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread{

	int port = 3000;
	String host = "localhost";
	boolean alive;

	public ClientThread(Client client){

	}

	public void start(){
		alive = true;
		while(alive){
			// TODO Auto-generated method stub
			Socket socket;
			BufferedReader in;
			PrintWriter out;

			try {
				socket = new Socket(InetAddress.getLocalHost(),port);
				System.out.println("Demande de connexion");

				TCP.writeProtocole(socket, Notification.QUERY_PRINT);
				Notification not = TCP.readProtocole(socket);
				if(not == Notification.REPLY_THREAD_WIP){

					TCP.fileTransfert(
							new FileInputStream("Client.txt"),
							socket.getOutputStream(),
							false);
					not = TCP.readProtocole(socket);
					if(not==Notification.REPLY_PRINT_OK){
						socket.close();
						System.out.println("Impression faite");
					}else{
						System.out.println("Erreur ServeurThread");
					}

				}else{
					System.out.println("le serveur est complétement bourré");
				}
				System.out.println("Fin du client");
				socket.close();

			}catch (UnknownHostException e) {

				e.printStackTrace();
			}catch (IOException e) {

				e.printStackTrace();
			}
		}

	}	

}
