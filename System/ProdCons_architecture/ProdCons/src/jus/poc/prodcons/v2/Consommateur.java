package jus.poc.prodcons.v2;
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

		super(2, observateur, tempsMoyenProduction, deviationTemps);
		this.tampon = tampon;
		nbrMsg = randToDo;
	}

	@Override
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbrMsg;
	}

	@Override
	public void run() {
		while(this.nbrMsg > 0){			
			treatment(getMessage());
		}
	}
	
	private Message getMessage(){
		Message tamp = null;
		try {
			tamp = tampon.get(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tamp != null)
			nbrMsg -=1;
		return tamp;		
	}
	
	private void treatment(Message m){
		int delay = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		if(m!=null){
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		}
	}
	
	public String name(){
		return this.getName()+"N"+this.identification();
	}
	


}
