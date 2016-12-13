package jus.poc.prodcons;

import java.util.Vector;

import com.sun.xml.internal.fastinfoset.sax.Properties;

public class ProdCons implements Tampon {
	private Vector<Message> buffer;
	private int buffer_size; 
	private Vector<Consommateur> Consotab = new Vector<Consommateur>();
	private Vector<Producteur> Productab = new Vector<Producteur>();
	private Observateur o;
	
	
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
	public synchronized Message get(Consommateur c) {
		System.out.println("Obtention d'accee de "+c.getName()+"N°"+c.identification()+"");
		 while(buffer.isEmpty()) {
	            try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }

		 
		Message tamp = buffer.remove(0);
		notifyAll();
		System.out.println("Notify from "+c.getName()+"N°"+c.identification()+"");
//		o.retraitMessage(c, tamp);
		return tamp;
		
	}

	@Override
	public synchronized void put(Producteur p, Message m) {
		System.out.println("Obtention d'accee de "+p.getName()+"N°"+p.identification()+"");
		while(buffer.size()==buffer_size) {
            try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

		buffer.add(m);
		notifyAll();
		System.out.println("Notify from "+p.getName()+"N°"+p.identification()+"");
//		o.depotMessage(p, m);
		System.out.println("Message ajouté");
	}

	@Override
	public int taille() {
		return buffer_size-buffer.size();
	}
	
	public Observateur getObservateur(){
		return o;
	}
	
	
}
