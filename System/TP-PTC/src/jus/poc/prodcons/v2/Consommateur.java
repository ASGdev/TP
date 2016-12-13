package jus.poc.prodcons.v2;

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
	
	
	
	
	private Message getMessage(){
		Message tamp = null;
		System.out.println("Demande d'accee de "+this.getName()+"N°"+this.identification()+"");
		tamp = tampon.get(this);
		nbMessage -=1;
		System.out.println("Tampon libre:"+tampon.taille()+" avec le retrait de "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");
		System.out.println("Sortie d'accee de "+this.getName()+"N°"+this.identification()+"");
		return tamp;		
	}
	
	private void treatment(Message m){
		if(m!=null){
			try {
				Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//tampon.getObservateur().consommationMessage(this, m, moyenneTempsDeTraitement);
			System.out.println("Traitement de consommation du thread "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");
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
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}
	
	
}
