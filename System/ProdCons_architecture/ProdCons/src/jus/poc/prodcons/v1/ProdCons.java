package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;
import java.util.Vector;


public class ProdCons implements Tampon{
	int taille;
	private Vector<Message> buffer = new  Vector<Message>();
	private static final Object verrou = new Object();

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
		Message g = null;
		synchronized(verrou) {
			while(buffer.size()==0) {
				verrou.wait();
			}
			g= buffer.firstElement();
			buffer.remove(0);
			System.out.println("Le thread consomateur "+arg0.identification()+" a retire le message "+g);
			verrou.notifyAll();

		}
		return g;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		synchronized(verrou) {
			while(buffer.size()>=taille) {
				verrou.wait();
			}
			System.out.println("Le thread producteur "+arg0.identification()+" a poste le message "+arg1);
			buffer.addElement(arg1);
			verrou.notifyAll();

		}

	}

	@Override
	public int taille() {
		return buffer.size();
	}
}
