package jus.poc.prodcons;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import com.sun.xml.internal.fastinfoset.sax.Properties;

public class ProdCons implements Tampon {
	private Vector<Message> buffer;
	private int buffer_size; 
	private Vector<Consommateur> Consotab = new Vector<Consommateur>();
	private Vector<Producteur> Productab = new Vector<Producteur>();
	private Observateur o;
	private Semaphore semDispo;
	private Semaphore semTampon;
	
	public ProdCons(int nbProd, int nbCons, Observateur o){
		
		for(int i=0;i<nbCons;i++){
			Consommateur c = new Consommateur(2,o,5,2,this);
			Consotab.add(c);
			this.o=o;
		}
		
		for(int i=0;i<nbProd;i++){
			Producteur p = new Producteur(1,o,5,2,this);
			Productab.add(p);
		}		
		buffer_size=6;//use option to configure it
		buffer = new Vector<Message>();
		semDispo = new Semaphore(0);
		semTampon = new Semaphore(buffer_size);
		
		
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
		Message tamp =null;
		try{
			semDispo.acquire();
			System.out.println("Obtention d'accee de "+c.getName()+"N°"+c.identification()+"");
			tamp = buffer.remove(0);
			o.retraitMessage(c, tamp);
			semTampon.release();
			System.out.println("increment of semTampon from "+c.getName()+"N°"+c.identification()+"");
		}catch(InterruptedException e) {
	        e.printStackTrace();
	    }		
		
		return tamp;
		
	}

	@Override
	public void put(Producteur p, Message m) {
			
		try{
			semTampon.acquire();
			System.out.println("Obtention d'accee de "+p.getName()+"N°"+p.identification()+"");
			buffer.add(m);
			o.depotMessage(p, m);
			semDispo.release();
			System.out.println("increment of semDispo from "+p.getName()+"N°"+p.identification()+"");
		}catch(InterruptedException e) {
	        e.printStackTrace();
	    }		
		
		System.out.println("Message ajouté");
	}

	@Override
	public int taille() {
		return buffer_size-buffer.size();
	}
	
	public Observateur getObservateur(){
		return o;
	}
	
	public void addConsommateur(){
		
		Consommateur c = new Consommateur(2,o,5,2,this);
		Consotab.add(c);
	}
	
	public void addProducteur(){
		
		Producteur p = new Producteur(1,o,5,2,this);
		Productab.add(p);
	}
	
	
}
