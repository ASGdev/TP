package jus.poc.prodcons;

public class ProdCons implements Tampon {
	private Observateur o = new Observateur();
	private Consommateur c = new Consommateur(2,o,5,2);
	private Producteur p = new Producteur(1,o,5,2); 
	
	ProdCons(){
		exec();
	}
	
	public void exec(){
		p.run();
		c.run();
	}
	
	
}
