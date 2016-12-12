package jus.poc.prodcons;

import java.util.Properties;

public abstract class Simulateur {
	
	protected Observateur observateur;
	public static Properties options;
	public Simulateur(Observateur observateur) {
		
	}
	
	protected void run() throws Exception{};
	void start(){};
	


}
