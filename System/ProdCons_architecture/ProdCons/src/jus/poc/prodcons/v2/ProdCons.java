package jus.poc.prodcons.v2;
import jus.poc.prodcons.*;
import java.util.Vector;
import java.util.concurrent.Semaphore;


public class ProdCons implements Tampon{
	int taille;
	private Vector<Message> buffer = new  Vector<Message>();
	private final Object verrou = new Object();
	private final Object verrou2 = new Object();
	private  Semaphore depot ;
	private  Semaphore retrait = new Semaphore(0);

	//CEST ICI QUON VA GERER LES "VERROUS" ETC
    
    boolean stop = false;
        

	public ProdCons(int taille){
		depot = new Semaphore(taille);
		this.taille = taille;
	}
	
	
	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return 0;
	}

	//Dans cette version, un consommateur peut récupéré un élement tandis qu'un producteur peut en déposé
	// un simultanement. Par contre, deux consomateur ou deux producteur ne peuvent agir de manière simultanée.
	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		retrait.acquire();
		Message g = null;
		synchronized(verrou) {
			g = buffer.firstElement();
			buffer.remove(0);
		}
		depot.release();
		return g;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		depot.acquire();
		synchronized(verrou2) {
			buffer.addElement(arg1);
		}
		retrait.release();

	}

	@Override
	public int taille() {
		return buffer.size();
	}
}
