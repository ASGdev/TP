package jus.poc.prodcons;

public class Producteur extends Acteur implements _Producteur {
	private final Object lockTamponProd = new Object();
	ProdCons tampon;

	public Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tmp) {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tmp;
	}
	
	public void run(){
		while(this.nbMessage >0){
			MsgInteger m = new MsgInteger(this.nbMessage);
			addMessage(m);
			try {
				Thread.sleep(Aleatoire.next());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void addMessage(Message m){
		
		System.out.println("Demande d'accee de la part de "+this.getName()+"N°"+this.identification());
		synchronized (lockTamponProd) {
			System.out.println("Obtention d'accee pour "+this.getName()+"N°"+this.identification());
			if(tampon.taille()>0){
				tampon.put(this,m);
				System.out.println("tampon libre :"+tampon.taille()+" avec l'ajout du thread "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");
				this.nbMessage -= 1;
			}
			System.out.println("Sortie d'accee pour "+this.getName()+"N°"+this.identification());
		}
		
	}
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}

}
