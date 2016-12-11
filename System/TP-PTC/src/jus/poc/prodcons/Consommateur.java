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
	
	public void start(){
		for(int i =0;i<10;i++){
			System.out.println(this.getName()+"N°"+this.identification());
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
		while(this.nbMessage > 0){
			getMessage();
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
				System.out.println("tampon libre :"+tampon.taille()+" avec le retrait du thread "+this.getName()+"N°"+this.identification());
				this.nbMessage -= 1;
			}				
			System.out.println("Sortie d'accee pour "+this.getName()+"N°"+this.identification());
		}
	}
	
	
}
