package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;
import java.util.Vector;


public class ProdCons implements Tampon{
	int taille;
	private Vector<Message> buffer = new  Vector<Message>();
	private final Object verrou = new Object();

	//CEST ICI QUON VA GERER LES "VERROUS" ETC
    
    boolean stop = false;
        

	public ProdCons(int taille){
		this.taille = taille;
	}
	
	
	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		synchronized(verrou) {
	        if(buffer.size()==0) {
	        	verrou.wait();
	        }else {
	        	verrou.notifyAll();
	        	return buffer.firstElement();
	        }
	    }
		return null;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		synchronized(verrou) {
	        if(buffer.size()<taille) {
	        	buffer.addElement(arg1);
	        	verrou.notifyAll();
	        }else {
	        	verrou.wait();
	        }
	    }
		
	}

	@Override
	public int taille() {
		return buffer.size();
	}
}
