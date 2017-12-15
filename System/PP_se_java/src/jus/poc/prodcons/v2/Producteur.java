package jus.poc.prodcons.v2;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {
	int messToDo;
	int messDone;
	
	Aleatoire rand;
    ProdCons ress; // Buffer pour les messages
	
	protected Producteur(Observateur observateur,
						 int tempsMoyenProduction,
						 int deviationTemps,
						 ProdCons ress,
						 int randToDo)
			  throws ControlException {
		
		super(1, observateur, tempsMoyenProduction, deviationTemps);
		
		rand = new Aleatoire(tempsMoyenProduction, deviationTemps);
        messToDo = randToDo;
		this.ress = ress;
	}
	
	public void run(){
		for(messDone = 0 ; messDone < messToDo; messDone++){
			try {
				sleep(rand.next());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				ress.put(this, new MessageX(this.identification(), messDone));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int nombreDeMessages() {
		return messToDo;
	}
	
	

}
