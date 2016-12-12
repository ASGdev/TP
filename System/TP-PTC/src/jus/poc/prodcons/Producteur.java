package jus.poc.prodcons;

public class Producteur extends Acteur implements _Producteur {
	
	ProdCons tampon;

	public Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tmp) {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tmp;
	}
	
	public void run(){
		while(this.nbMessage >0){
			
			addMessage(production());
		}
	}
	
	
	private void addMessage(Message m){
		
		System.out.println("Demande d'accee de la part de "+this.getName()+"N°"+this.identification());
		synchronized (lockTamponProd) {
			System.out.println("Obtention d'accee pour "+this.getName()+"N°"+this.identification());
			if(tampon.taille()>0){
				tampon.put(this,m);
				this.nbMessage -= 1;
				System.out.println("tampon libre :"+tampon.taille()+" avec l'ajout du thread "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");
				
			}
			System.out.println("Sortie d'accee pour "+this.getName()+"N°"+this.identification());
		}
		
	}
	
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}
	
	private Message production(){
		MsgInteger m = new MsgInteger(this.nbMessage);
		try {
			Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
			Observateur.productionMessage(this, m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Traitement de production du thread "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");

		return m;
		
	}

}
