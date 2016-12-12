package jus.poc.prodcons;

import java.util.Vector;

public class ProdCons implements Tampon {
	private Vector<Message> buffer;
	private int buffer_size; 
	private Vector<Consommateur> Consotab = new Vector<Consommateur>();
	private Vector<Producteur> Productab = new Vector<Producteur>();
	private Observateur o = new Observateur();
	
	
	public ProdCons(int nbProd, int nbCons){
		
		for(int i=0;i<nbCons;i++){
			Consommateur c = new Consommateur(2,o,5,2,this);
			Consotab.add(c);
		}
		
		for(int i=0;i<nbProd;i++){
			Producteur p = new Producteur(1,o,5,2,this);
			Productab.add(p);
		}
		buffer_size=6;//use option to configure it
		buffer = new Vector<Message>();
		
		
		exec();
	}
	
	public void exec(){
		for(int i=0;i<Productab.size();i++){
			//System.out.println(Productab.get(i).name());
			Productab.get(i).start();
		}
		 
		for(int i=0;i<Consotab.size();i++){
			//System.out.println(Consotab.get(i).name());
			Consotab.get(i).start();
		}
		
		
		//System.out.println("");
	}

	@Override
	public int enAttente() {
		return buffer.size();
	}

	@Override
	public Message get(Consommateur c) {
		Message tamp = buffer.remove(0);
		Observateur.retraitMessage(c, tamp);
		return tamp;
		
	}

	@Override
	public void put(Producteur p, Message m) {
		buffer.add(m);
		Observateur.depotMessage(p, m);
		System.out.println("Message ajouté");
	}

	@Override
	public int taille() {
		return buffer_size-buffer.size();
	}
	
	
}
