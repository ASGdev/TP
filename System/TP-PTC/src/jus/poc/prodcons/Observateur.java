package jus.poc.prodcons;

import java.util.Vector;

public class Observateur {
	
	Vector<Vector<Object>> messagesConsommes;
	Vector<Vector<Object>> depot;
	Vector<Consommateur> consommateurs;
	Vector<Producteur> producteurs;
	Vector<Vector<Object>> messagesProduits;
	Vector<Vector<Object>> messagesRetires;
	
	Observateur(){
		Vector<Vector<Object>> messagesConsommes= new Vector<Vector<Object>>();
		Vector<Vector<Object>> depot = new Vector<Vector<Object>>();
		Vector<Consommateur> consommateur = new Vector<Consommateur>();
		Vector<Producteur> producteur = new Vector<Producteur>();
		Vector<Vector<Object>> messagesProduits = new Vector<Vector<Object>>();
		Vector<Vector<Object>> messagesRetires= new Vector<Vector<Object>>();
	}
	
	void consommationMessage(Consommateur c, Message m, int delay){
		Vector<Object> v = new Vector<Object>();
		v.addElement(c);v.addElement(m);v.addElement(delay);
		messagesConsommes.add(0, v);
	}
	
	void depotMessage(Producteur p, Message m){
		Vector<Object> v = new Vector<Object>();
		v.addElement(p);v.addElement(m);
		depot.add(0, v);
	}
	
	void newConsommateur(Consommateur c){
		consommateurs.add(0, c);
	}
	
	void newProducteur(Producteur c){
		producteurs.add(0, c);
	}
	
	void productionMessage(Producteur p,Message m){
		Vector<Object> v = new Vector<Object>();
		v.addElement(p);v.addElement(m);
		messagesProduits.add(0, v);
	}
	
	void retraitMessage(Consommateur c, Message m){
		Vector<Object> v = new Vector<Object>();
		v.add(c);v.add(m);
		messagesRetires.add(0, v);
	}
	
}
