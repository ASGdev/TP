package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;
import java.util.Vector;


public class ProdCons implements Tampon{
	int taille;
	private Message buffers[];
	int head=0;
	int tail=0;
    int messWaiting;
 
    
    boolean stop = false;
        
	//Alloue le buffer
	public ProdCons(int taille){
		this.taille = taille;
		messWaiting = 0;
		buffers = new Message[taille];
	}
	Vector<MessageX> tampon = new Vector<>();
	//CEST ICI QUON VA GERER LES "VERROUS" ETC
	
	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int taille() {
		return tampon.size();
	}
}
