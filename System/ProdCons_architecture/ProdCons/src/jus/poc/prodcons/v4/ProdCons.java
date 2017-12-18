package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;
import java.util.Vector;
import java.util.concurrent.Semaphore;


public class ProdCons implements Tampon{
	int taille;
	private Vector<Vector<Message>> buffer = new  Vector<Vector<Message>>();
	private Vector<_Consommateur> consoMemory = new Vector<_Consommateur>();
	private Vector<_Producteur> prodMemory = new Vector<_Producteur>();
	private static final Object verrou = new Object();
	private static final Object verrou2 = new Object();
	private int nbconsomateur =0;

	//CEST ICI QUON VA GERER LES "VERROUS" ETC
        

	public ProdCons(int taille,int nbconsomateur, int nbproducer){
		this.taille = taille;
		this.nbconsomateur = nbconsomateur;
	}
	
	
	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return buffer.size();
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		boolean jobdone=false;
		Message g = null;
		while(!jobdone) {
			synchronized(verrou) {
				if(!consoMemory.contains(arg0) && !buffer.isEmpty() ) {

					consoMemory.add(arg0);
					g = buffer.firstElement().firstElement();
					buffer.firstElement().remove(0);
					System.out.println("Le thread consomateur n°"+arg0.identification()+" a retiré le message ("+g+")");
					jobdone = true;
					if(buffer.firstElement().size()<=0) {
						buffer.remove(0);
						prodMemory.remove(0);
						consoMemory.clear();
										

					}
				}else {

				}
			}
		}
		return g;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		boolean jobdone=false;
		while(!jobdone) {
			synchronized(verrou2) {
				if(!prodMemory.contains(arg0) && buffer.size() <taille) {
					prodMemory.addElement(arg0);
					Vector<Message> msg = new Vector<Message>();
					for(int i=0;i<nbconsomateur;i++) {
						msg.addElement(arg1);
					}
					buffer.addElement(msg);
					System.out.println("Le thread producteur n°"+arg0.identification()+" a posé le message ("+arg1+")");
					jobdone = true;
				}else {

				}
			}
		}	
	}

	@Override
	public int taille() {
		return buffer.size();
	}
}
