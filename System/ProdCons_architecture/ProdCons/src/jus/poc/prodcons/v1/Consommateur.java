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
		System.out.println("Demande d'accee de "+this.getName()+"N°"+this.identification()+"");
		try {
			tamp = tampon.get(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nbrMsg -=1;
		System.out.println("Tampon libre:"+tampon.taille()+" avec le retrait de "+name()+", reste "+nbrMsg+"a traiter");
		System.out.println("Sortie d'accee de "+name());
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
			System.out.println("Traitement de consommation du thread "+name()+", reste "+nbrMsg+"a traiter");
		}else{
			System.out.println("GetMessage a renvoyé null, rien a faire pour thread "+name());
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		}
	}
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}
	


}
