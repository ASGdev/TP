
public class Simulation {
	int Threadpool = 10;
	
	public Simulation(int n) {
		this.Threadpool = n;
	}
	
	public static void main(int[] args) {
		// TODO Auto-generated method stub
		Simulation simulation = new Simulation((args[0]));
		Client client = new Client();
		for (int i = 0; i < simulation.Threadpool; i++) {
			
			ClientThread clientthread = new ClientThread(client);
			clientthread.mainTCP();
		}
	}
}
