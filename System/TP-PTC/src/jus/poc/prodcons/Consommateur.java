package jus.poc.prodcons;

public class Consommateur extends Acteur implements _Consommateur {
	ProdCons tampon;
	 
	public Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, ProdCons tmp) {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tmp;
	}
	
	public void run(){
		
		while(this.nbMessage > 0){
			
			treatment(getMessage());
		}
		
	}
	
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}
	
	private Message getMessage(){
		Message tamp = null;
		
		System.out.println("Demande d'accee de la part de "+this.getName()+"N°"+this.identification());
		synchronized (lockTamponCons) {
			System.out.println("Obtention d'accee pour "+this.getName()+"N°"+this.identification());
			if(tampon.enAttente()>0){
				 tamp = tampon.get(this);
				this.nbMessage -= 1;
				System.out.println("tampon libre :"+tampon.taille()+" avec le retrait du thread "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");
								
			}
			System.out.println("Sortie d'accee pour "+this.getName()+"N°"+this.identification());
			return tamp;
								
			
		}
	}
	
	private void treatment(Message m){
		try {
			Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Observateur.consommationMessage(this, m, moyenneTempsDeTraitement);
		System.out.println("Traitement de consommation du thread "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");

	}
	
	
}
