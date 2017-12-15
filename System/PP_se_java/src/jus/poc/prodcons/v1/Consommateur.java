package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;



public class Consommateur extends Acteur implements _Consommateur {
    Aleatoire rand;
    ProdCons ress;
    int mess;
    boolean stop = false;

    protected Consommateur(Observateur observateur,
    					   int tempsMoyenConsommation,
    					   int deviationTemps,
    					   ProdCons ress)
    				throws ControlException {
        super(2, observateur, tempsMoyenConsommation, deviationTemps);
        rand = new Aleatoire(tempsMoyenConsommation,deviationTemps);
        this.ress = ress;
    }
    
    public void stopMe(){
    	this.stop = true;
    }
    public boolean isStopped(){
    	return stop;
    }
    
	@Override
	public void run() {
		while(!stop){
			try {
				sleep(rand.next());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				ress.get(this);
				mess++;
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public int nombreDeMessages() {
		return mess;
	}
}
