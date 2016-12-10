package jus.poc.prodcons;

public class ProdCons implements Tampon {
	private int[] buffer;
	private Observateur o = new Observateur();
	private Consommateur c = new Consommateur(2,o,5,2);
	private Producteur p = new Producteur(1,o,5,2); 
	
	ProdCons(){
		exec();
	}
	
	public void exec(){
		
		buffer = new int[6]; //use option to configure it
		p.run();
		c.run();
	}

	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Message get(Consommateur c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Producteur p, Message m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
