
public class Simulation {
	int Threadpool = 10;
	
	public Simulation(int n) {
		this.Threadpool = n;
	}
	
	public static void main(int[] args) {
		// TODO Auto-generated method stub
		Simulation simulation = new Simulation((args[0]));
		for (int i = 0; i < simulation.Threadpool; i++) {
			Client client = new Client();
			ClientThread clientthread = new ClientThread(client);
			client.mainTCP();
		}
	}
}
