package jus.poc.prodcons.v3;
import jus.poc.prodcons.*;



public class Consommateur extends Acteur implements _Consommateur {
    Aleatoire rand;
    ProdCons ress;
    int mess;
    boolean stop = false;
    Observateur observateur;

    protected Consommateur(Observateur observateur,
    					   int tempsMoyenConsommation,
    					   int deviationTemps,
    					   ProdCons ress)
    				throws ControlException {
        super(2, observateur, tempsMoyenConsommation, deviationTemps);
        rand = new Aleatoire(tempsMoyenConsommation,deviationTemps);
        this.ress = ress;
        this.observateur = observateur;
    }
    
    public void stopMe(){
    	this.stop = true;
    }
    public boolean isStopped(){
    	return stop;
    }
    
	@Override
	public void run() {
		int tmp = 0;
		MessageX tmpx;
		while(!stop){
			try {
				tmp = rand.next();
				sleep(tmp);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				tmpx = (MessageX) ress.get(this);
				observateur.consommationMessage(this, tmpx, tmp);
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
