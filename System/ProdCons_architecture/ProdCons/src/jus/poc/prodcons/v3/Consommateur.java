package jus.poc.prodcons.v3;
import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {
	ProdCons tampon;
	int idCons;
	int nbrMsg;
	Observateur observateur;
	protected Consommateur(Observateur observateur,
			int tempsMoyenProduction,
			int deviationTemps,
			ProdCons tampon,
			int randToDo)
					throws ControlException {

		super(2, observateur, tempsMoyenProduction, deviationTemps);
		this.tampon = tampon;
		nbrMsg = randToDo;
		this.observateur = observateur;
	}

	@Override
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbrMsg;
	}

	@Override
	public void run() {
		while(this.nbrMsg > 0){			
			try {
				treatment(getMessage());
			} catch (InterruptedException | ControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	private void treatment(Message m) throws InterruptedException, ControlException{
		int delay = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		if(m!=null){
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			observateur.consommationMessage(this, m, delay);			
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
		return this.getName()+" N "+this.identification();
	}
	


}
