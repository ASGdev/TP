package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {
	int messToDo;
	int messDone;
	
	Observateur observateur;
	
	Aleatoire rand;
	Aleatoire randNbEx;
    ProdCons ress; // Buffer pour les messages
	
	protected Producteur(Observateur observateur,
						 int tempsMoyenProduction,
						 int deviationTemps,
						 ProdCons ress,
						 int randToDo,
						 int nombreMoyenNbExemplaire,
						 int deviationNombreMoyenNbExemplaire)
			  throws ControlException {
		
		super(1, observateur, tempsMoyenProduction, deviationTemps);
		
		rand = new Aleatoire(tempsMoyenProduction, deviationTemps);
		randNbEx  = new Aleatoire(nombreMoyenNbExemplaire,deviationNombreMoyenNbExemplaire);
        messToDo = randToDo;
		this.ress = ress;
		this.observateur = observateur;
	}
	

	public void run(){
		int tmp = 0;
		MessageX tmpx;
		for(messDone = 0 ; messDone < messToDo; messDone++){
			try {
				tmp = rand.next();
				sleep(tmp);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				tmpx = new MessageX(this.identification(), messDone, randNbEx.next());
				ress.put(this, tmpx);
				observateur.productionMessage(this, tmpx, tmp);
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
