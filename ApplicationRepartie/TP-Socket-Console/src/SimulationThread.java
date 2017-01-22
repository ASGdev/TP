import java.net.Socket;

public class SimulationThread extends Thread{

private Simulation mainSimul;
	
	public SimulationThread(Simulation simulation){
		mainSimul = simulation;
	}
	
	public void start(){
		
	}	
	
	private void readNotification(Socket soc){
		
	}
	
	private void writeNotification(Socket soc, Notification n){
		
	}
	
	private void readData(Socket soc){
		
	}
	
	private void writeData(Socket soc, String s){
		
	}

}
