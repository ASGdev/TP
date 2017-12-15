package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;


public class ProdCons implements Tampon {
	int taille;
	private Message buffers[];
	int head=0;
	int tail=0;
    int messWaiting;
    Display display;
    
    boolean stop = false;
        
	
	public ProdCons(int taille, Display display){
		this.taille = taille;
		messWaiting = 0;
		buffers = new Message[taille];
        this.display = display;
	}
	
	@Override
	public int enAttente() {
		return (tail - head) % taille;
	}
	
	public void stopMe(){
		stop = true;
	}
	
	@Override
	public synchronized Message get(_Consommateur c) throws Exception,InterruptedException{
		while(messWaiting == 0 && !stop){
			wait();
        }
		if(stop)
			return new MessageX("Consommateur "+c.identification()+" terminé");
		
		Message m = buffers[head];
		head = (head + 1) % taille;
		messWaiting--;
        display.disp("Consommateur "+c.identification()+" -- "+m);
		notifyAll();
		return m;
	}

	@Override
	public synchronized void put(_Producteur p, Message m) throws Exception,InterruptedException {
		while(messWaiting == taille) {
			wait();
        }
		buffers[tail] = m;
		tail = (tail + 1) % taille;
		messWaiting++;
        display.disp("Producteur "+p.identification()+" -- "+m);
		notifyAll();
	}

	@Override
	public int taille() {
		return taille;
	}
        
    public boolean consumed(){
        return messWaiting == 0;
    }

}
