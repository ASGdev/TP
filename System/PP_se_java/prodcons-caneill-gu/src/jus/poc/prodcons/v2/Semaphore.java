package jus.poc.prodcons.v2;
//import jus.poc.prodcons.*;



public class Semaphore {
    int ress;
    
    public Semaphore(int ress) {
        this.ress = ress;
    }
    
    public synchronized void P() throws InterruptedException {
    	ress--;
        if(ress < 0)
        	wait();
    }
    
    public synchronized void V() {
    	ress++;
        if(ress <= 0)
            notify();
    }
}
