package jus.poc.prodcons.v3;
import jus.poc.prodcons.*;


public class ProdCons implements Tampon {
	int taille;
	private Message buffers[];
	int head = 0;
	int tail = 0;
    int messWaiting; // messages en attente
    Display display;
    
    /* sémaphores */
    private Semaphore semProd; // production
    private Semaphore semCons; // consommation
    private Semaphore semMutex; // buffer
    
    boolean stop = false;
    
    Observateur observateur;
        
	
	public ProdCons(int taille, Display display, Observateur observateur){
		this.taille = taille;
		messWaiting = 0;
		buffers = new Message[taille];
        this.display = display;
        
        semProd = new Semaphore(taille);
        semCons = new Semaphore(0);
        semMutex = new Semaphore(1);
        
        this.observateur = observateur;
	}
	
	@Override
	public int enAttente() {
		return (tail - head) % taille;
	}
	
	public void stopMe(){
		//stop = true;
		;//semCons.stopMe();
	}
	
	@Override
	public Message get(_Consommateur c) throws Exception,InterruptedException{
		semCons.P();
		semMutex.P();
		
		Message m = buffers[head];
		observateur.retraitMessage(c, m);
		head = (head + 1) % taille;
		messWaiting--;
        display.disp("Consommateur "+c.identification()+" -- "+m);
        
        semMutex.V();
        semProd.V();
        
		return m;
	}

	@Override
	public void put(_Producteur p, Message m) throws Exception,InterruptedException {
		semProd.P();
		semMutex.P();
		
		buffers[tail] = m;
		observateur.depotMessage(p, m);
		tail = (tail + 1) % taille;
		messWaiting++;
        display.disp("Producteur "+p.identification()+" -- "+m);

        semMutex.V();
        semCons.V();
	}

	@Override
	public int taille() {
		return taille;
	}
        
    public boolean consumed(){
        return messWaiting == 0;
    }

}
