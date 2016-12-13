package jus.poc.prodcons.v1;

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
		System.out.println("Demande d'accee de "+this.getName()+"N°"+this.identification()+"");
		tampon.put(this,m);
		nbMessage -=1;
		System.out.println("Tampon libre:"+tampon.taille()+" avec l'ajout de "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");
		System.out.println("Sortie d'accee de "+this.getName()+"N°"+this.identification()+"");	
		
	}
	
	
	
	
	private Message production(){
		MsgInteger m = new MsgInteger(this.nbMessage);
		try {
			Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tampon.getObservateur().productionMessage(this, m);
		System.out.println("Traitement de production du thread "+this.getName()+"N°"+this.identification());

		return m;
		
	}
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}

}
