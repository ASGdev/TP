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
			System.out.println(arg0.identification()+"est entré");
			while(buffer.size()==0) {
				System.out.println(arg0.identification()+"se met en pause et libère");
				verrou.wait();
			}
			g= buffer.firstElement();
			buffer.remove(0);
			System.out.println(arg0.identification()+" sort après avoir enlever un msg");
			verrou.notifyAll();

		}
		return g;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		synchronized(verrou) {
			System.out.println(arg0.identification()+"est entré");
			while(buffer.size()>=taille) {
				System.out.println(arg0.identification()+" se met en pause et libère");
				verrou.wait();
			}			
			buffer.addElement(arg1);
			System.out.println(arg0.identification()+" sort après avoir ajouter un msg");
			verrou.notifyAll();

		}

	}

	@Override
	public int taille() {
		return buffer.size();
	}
}
