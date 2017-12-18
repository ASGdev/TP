package jus.poc.prodcons.v2;
import jus.poc.prodcons.*;
import java.util.Vector;
import java.util.concurrent.Semaphore;


public class ProdCons implements Tampon{
	int taille;
	private Vector<Message> buffer = new  Vector<Message>();
	private static final Object verrou = new Object();
	private static final Object verrou2 = new Object();
	private static  Semaphore depot ;
	private static  Semaphore retrait = new Semaphore(0);

	//CEST ICI QUON VA GERER LES "VERROUS" ETC
    
    boolean stop = false;
        

	public ProdCons(int taille){
		depot = new Semaphore(taille);
		this.taille = taille;
	}
	
	
	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return buffer.size();
	}

	//Dans cette version, un consommateur peut r�cup�r� un �lement tandis qu'un producteur peut en d�pos�
	// un simultanement. Par contre, deux consomateur ou deux producteur ne peuvent agir de mani�re simultan�e.
	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		retrait.acquire();
		Message g = null;
		synchronized(verrou) {
			g = buffer.firstElement();
			buffer.remove(0);
			System.out.println("Le thread consomateur "+arg0.identification()+" a retire le message "+g+" ");
		}
		depot.release();
		return g;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		depot.acquire();
		synchronized(verrou2) {
			buffer.addElement(arg1);
			System.out.println("Le thread producteur "+arg0.identification()+" a poste le message "+arg1+" ");
		}
		retrait.release();

	}

	@Override
	public int taille() {
		return buffer.size();
	}
}
