package jus.poc.prodcons.v2;

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
	
	 public void newConsommateur(Consommateur c){
		evenementJournal.add("Consommateur "+c.getName()+"N°"+c.identification()+" ajouté");
		
	}
	
	public void newProducteur(Producteur c){
		evenementJournal.add("Producteur "+c.getName()+"N°"+c.identification()+" ajouté");
	}
	
	 public void productionMessage(Producteur p,Message m){
		 prodJournal.add("Producteur "+p.getName()+"N°"+p.identification()+" vient de creer un message");

	}
	
	 public void retraitMessage(Consommateur c, Message m){
		 consoJoural.add("Consommateur "+c.getName()+"N°"+c.identification()+" vient de retirer un message à"+System.currentTimeMillis());

	}
	
	
	
}
