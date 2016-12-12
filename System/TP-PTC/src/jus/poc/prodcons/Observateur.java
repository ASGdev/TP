package jus.poc.prodcons;

import java.util.Vector;

public class Observateur {
	
	static Vector<Vector<Object>> messagesConsommes;
	static Vector<Vector<Object>> depot;
	static Vector<Consommateur> consommateurs;
	static Vector<Producteur> producteurs;
	static Vector<Vector<Object>> messagesProduits;
	static Vector<Vector<Object>> messagesRetires;
	
	Observateur(){
		Vector<Vector<Object>> messagesConsommes= new Vector<Vector<Object>>();
		Vector<Vector<Object>> depot = new Vector<Vector<Object>>();
		Vector<Consommateur> consommateur = new Vector<Consommateur>();
		Vector<Producteur> producteur = new Vector<Producteur>();
		Vector<Vector<Object>> messagesProduits = new Vector<Vector<Object>>();
		Vector<Vector<Object>> messagesRetires= new Vector<Vector<Object>>();
	}
	
	static void consommationMessage(Consommateur c, Message m, int delay){
		Vector<Object> v = new Vector<Object>();
		v.addElement(c);v.addElement(m);v.addElement(delay);
		messagesConsommes.add(0, v);
	}
	
	static void depotMessage(Producteur p, Message m){
		Vector<Object> v = new Vector<Object>();
		v.addElement(p);v.addElement(m);
		depot.add(0, v);
	}
	
	static void newConsommateur(Consommateur c){
		consommateurs.add(0, c);
	}
	
	static void newProducteur(Producteur c){
		producteurs.add(0, c);
	}
	
	static void productionMessage(Producteur p,Message m){
		Vector<Object> v = new Vector<Object>();
		v.addElement(p);v.addElement(m);
		messagesProduits.add(0, v);
	}
	
	static void retraitMessage(Consommateur c, Message m){
		Vector<Object> v = new Vector<Object>();
		v.add(c);v.add(m);
		messagesRetires.add(0, v);
	}
	
	
	
}
