package jus.poc.prodcons.v2;

public interface Tampon {
	
	int enAttente();
	Message get(Consommateur c);
	void put(Producteur p, Message m);
	int taille();
	

}
