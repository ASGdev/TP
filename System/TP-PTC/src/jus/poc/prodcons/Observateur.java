package jus.poc.prodcons;

import java.util.Vector;

public class Observateur {
	
	private  Vector<Vector<Object>> messagesConsommes;
	private  Vector<Vector<Object>> depot;
	public  Vector<Consommateur> consommateurs;
	public  Vector<Producteur> producteurs;
	private  Vector<Vector<Object>> messagesProduits;
	private  Vector<Vector<Object>> messagesRetires;
	
	Observateur(){
		Vector<Vector<Object>> messagesConsommes= new Vector<Vector<Object>>();
		Vector<Vector<Object>> depot = new Vector<Vector<Object>>();
		Vector<Consommateur> consommateur = new Vector<Consommateur>();
		Vector<Producteur> producteur = new Vector<Producteur>();
		Vector<Vector<Object>> messagesProduits = new Vector<Vector<Object>>();
		Vector<Vector<Object>> messagesRetires= new Vector<Vector<Object>>();
		
	}
	
	 public void consommationMessage(Consommateur c, Message m, int delay){
		Vector<Object> v = new Vector<Object>();
		v.add(c.getName());v.add(m.getContent());v.add(delay);
		messagesConsommes.add(v);
		 
	}
	
	 public void depotMessage(Producteur p, Message m){
		Vector<Object> v = new Vector<Object>();
		v.add(p.getName());v.add(m.getContent());
		depot.add(v);
	}
	
	/*static void newConsommateur(Consommateur c){
		consommateurs.add(0, c);
	}
	
	static void newProducteur(Producteur c){
		producteurs.add(0, c);
	}*/
	
	 public void productionMessage(Producteur p,Message m){
		/*Vector<Object> v = new Vector<Object>();
		v.add(p.getName());v.add(m.getContent());
		messagesProduits.add(v);*/
	}
	
	 public void retraitMessage(Consommateur c, Message m){
		/*Vector<Object> v = new Vector<Object>();
		v.add(c.getName());v.add(m.getContent());
		messagesRetires.add(v);*/
	}
	
	
	
}
