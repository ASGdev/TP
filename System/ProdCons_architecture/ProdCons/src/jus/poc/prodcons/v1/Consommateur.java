package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {
	ProdCons tampon;
	int idCons;
	int nbrMsg;
	
	protected Consommateur(Observateur observateur,
			 int tempsMoyenProduction,
			 int deviationTemps,
			 ProdCons tampon,
			 int randToDo)
					 throws ControlException {
	
	super(1, observateur, tempsMoyenProduction, deviationTemps);
	this.tampon = tampon;
	}

	@Override
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbrMsg;
	}

	@Override
	public void run() {
		System.out.println("ok");
	}
	


}
