package jus.poc.prodcons;

public class Producteur extends Acteur implements _Producteur {
	int nbMessage =0;
	ProdCons tampon;

	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement,int nbMessage) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deviationTempsDeTraitement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int identification() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moyenneTempsDeTraitement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}

	@Override
	public void run() {
		while(this.nbMessage > 0){			
			treatment(getMessage());
		}
	}
	
	private MessageX getMessage(){
		Message tamp = null;
		System.out.println("Demande d'accee de "+this.getName()+"N°"+this.identification()+"");
		tamp = tampon.get(this);
		nbMessage -=1;
		System.out.println("Tampon libre:"+tampon.taille()+" avec le retrait de "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");
		System.out.println("Sortie d'accee de "+this.getName()+"N°"+this.identification()+"");
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
			}			System.out.println("Traitement de consommation du thread "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");
		}else{
			System.out.println("Rien a faire pour thread "+this.getName()+"N°"+this.identification());
			try {
				Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		}	
	}

}
