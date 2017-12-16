package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {
	int nbMessage =0;
	int msg_remaining;
	int msg_send;
	int idProd;
	String message;
	ProdCons tampon;
	Aleatoire rand;
	
	
	
	protected Producteur(Observateur observateur,
			 int tempsMoyenProduction,
			 int deviationTemps,
			 ProdCons tampon,
			 int msgRemain)
 throws ControlException {

super(1, observateur, tempsMoyenProduction, deviationTemps);

rand = new Aleatoire(tempsMoyenProduction, deviationTemps);
//nbr de message à produire
msg_remaining = msgRemain;
this.tampon = tampon;
}
	
	@Override
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run() {
		
	}

}
