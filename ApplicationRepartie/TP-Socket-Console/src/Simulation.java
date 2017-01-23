
public class Simulation {
	static int ServThreadpool = 10;
	static int ClientThreadpool = 10;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server serv = new Server(ServThreadpool);
		
		Client client = new Client();
		for (int i = 0; i < ClientThreadpool; i++) {
			
			ClientThread clientthread = new ClientThread();
			clientthread.start();
		}
	}
}
