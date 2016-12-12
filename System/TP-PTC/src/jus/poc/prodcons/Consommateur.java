package jus.poc.prodcons;

public class Consommateur extends Acteur implements _Consommateur {
	private final Object lockTamponCons = new Object();
	ProdCons tampon;
	public Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, ProdCons tmp) {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tmp;
	}
	
	public void run(){
		
		while(this.nbMessage > 0){
			getMessage();
			try {
				Thread.sleep(Aleatoire.next());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}
	
	private void getMessage(){
		
		System.out.println("Demande d'accee de la part de "+this.getName()+"N°"+this.identification());
		synchronized (lockTamponCons) {
			System.out.println("Obtention d'accee pour "+this.getName()+"N°"+this.identification());
			if(tampon.enAttente()>0){
				tampon.get(this);
				this.nbMessage -= 1;
				System.out.println("tampon libre :"+tampon.taille()+" avec le retrait du thread "+this.getName()+"N°"+this.identification()+", reste "+nbMessage+"a traiter");

				
			}
			
								
			System.out.println("Sortie d'accee pour "+this.getName()+"N°"+this.identification());
		}
	}
	
	private void treatment(Message m){
		
	}
	
	
}
