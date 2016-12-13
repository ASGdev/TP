package jus.poc.prodcons;

import java.util.Vector;

public class Observateur {
	public Vector<String> prodJournal = new Vector<String>();
	public Vector<String> consoJoural = new Vector<String>();
	public Vector<String> evenementJournal = new Vector<String>();
	
	Observateur(){
		
		
	}
	
	 public void consommationMessage(Consommateur c, Message m, int delay){
		consoJoural.add("Consommateur "+c.getName()+"N°"+c.identification()+" à fini de traiter un message au bout de "+delay+"ms");
		 
	}
	
	 public void depotMessage(Producteur p, Message m){
		 consoJoural.add("Producteur "+p.getName()+"N°"+p.identification()+" vient de deposer un message à"+System.currentTimeMillis());

	}
	
	 /*static void newConsommateur(Consommateur c){
		consommateurs.add(0, c);
	}
	
	static void newProducteur(Producteur c){
		producteurs.add(0, c);*/
	
	 public void productionMessage(Producteur p,Message m){
		 prodJournal.add("Producteur "+p.getName()+"N°"+p.identification()+" vient de creer un message");

	}
	
	 public void retraitMessage(Consommateur c, Message m){
		 consoJoural.add("Consommateur "+c.getName()+"N°"+c.identification()+" vient de retirer un message à"+System.currentTimeMillis());

	}
	
	
	
}
