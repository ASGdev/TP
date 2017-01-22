import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	int port = 3000;
	String host = "localhost";

	void mainTCP() {
		// TODO Auto-generated method stub
		Socket socket;
		BufferedReader in;
		PrintWriter out;
		
		try {
			socket = new Socket(InetAddress.getLocalHost(),port);
			System.out.println("Demande de connexion");
	        in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
	        //String message_distant = in.readLine();
	        //System.out.println(message_distant);
            socket.close();

		}catch (UnknownHostException e) {
			
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
