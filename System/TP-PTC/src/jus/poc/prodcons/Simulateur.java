package jus.poc.prodcons;

import java.util.Properties;

public abstract class Simulateur {
	
	protected Observateur observateur;
	protected Properties options;
	public Simulateur(Observateur observateur) {
		// TODO Auto-generated constructor stub
	}
	
	protected void run() throws Exception{};
	void start(){};
	


}
