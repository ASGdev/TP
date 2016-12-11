package jus.poc.prodcons;

import java.util.Vector;

public class ProdCons implements Tampon {
	private Vector<Message> buffer;
	private int buffer_size; 
	private Vector<Consommateur> Consotab = new Vector<Consommateur>();
	private Vector<Producteur> Productab = new Vector<Producteur>();
	private Observateur o = new Observateur();
	
	
	public ProdCons(){
		Consommateur c = new Consommateur(2,o,5,2,this);
		Producteur p = new Producteur(1,o,5,2,this); 
		Consotab.add(c);
		Productab.add(p);
		exec();
	}
	
	public void exec(){
		buffer_size=6;//use option to configure it
		buffer = new Vector<Message>(); 
		for(int i=0;i<Consotab.size();i++){
			Consotab.get(i).run();;
		}
		for(int i=0;i<Productab.size();i++){
			Productab.get(i).run();;
		}
	}

	@Override
	public int enAttente() {
		return buffer.size();
	}

	@Override
	public Message get(Consommateur c) {
		
		return buffer.remove(0);
	}

	@Override
	public void put(Producteur p, Message m) {
		buffer.add(m);
		
	}

	@Override
	public int taille() {
		return buffer_size-buffer.size();
	}
	
	
}
