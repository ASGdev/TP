package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;
import java.util.Vector;


public class ProdCons implements Tampon{
	int taille;
	private Vector<Message> buffer;
	private final Object verrou = new Object();

	int head=0;
	int tail=0;
    int messWaiting;
    
	//CEST ICI QUON VA GERER LES "VERROUS" ETC
    
    boolean stop = false;
        
	//Alloue le buffer
	public ProdCons(int taille){
		this.taille = taille;
		messWaiting = 0;
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
