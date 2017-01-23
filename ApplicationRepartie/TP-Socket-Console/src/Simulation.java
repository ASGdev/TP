
public class Simulation {
	int ServThreadpool;
	int ClientThreadpool;
	
	public Simulation(int n,int m) {
		this.ServThreadpool = n;
		this.ClientThreadpool = m;
	}
	
	public static void main(int[] args) {
		// TODO Auto-generated method stub
		Simulation simulation = new Simulation(args[0],args[1]);
		Server serv = new Server(10);
		
		Client client = new Client();
		for (int i = 0; i < simulation.ClientThreadpool; i++) {
			
			ClientThread clientthread = new ClientThread(client);
			clientthread.start();
		}
	}
}
