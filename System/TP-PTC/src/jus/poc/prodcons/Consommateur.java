package jus.poc.prodcons;

public class Consommateur extends Acteur implements _Consommateur {
	
	ProdCons tampon;
	public Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, ProdCons tmp) {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tmp;
	}
	
	public void start(){
		run();
	}
	
	public void run(){
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
	
	synchronized void getMessage(){
		while(tampon.enAttente()== 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tampon.get(this);
		System.out.println("tampon libre :"+tampon.taille()+" avec le retrait du thread "+this.getName()+"N°"+this.identification());
		this.nbMessage -= 1;
		notify();		
	}
	
	
}
