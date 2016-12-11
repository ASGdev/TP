package jus.poc.prodcons;

public class Producteur extends Acteur implements _Producteur {
	
	ProdCons tampon;

	public Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tmp) {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tmp;
	}
	
	public void run(){
		for(int i =0;i<10;i++){
			System.out.println(this.getName()+"N°"+this.identification());
		}
		/*while(this.nbMessage >0){
			MsgInteger m = new MsgInteger(this.nbMessage);
			
			
		}*/
		
		
	}
	
	synchronized void addMessage(Message m){
		while(tampon.taille()== 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tampon.put(this, m);
		notify();
		
	}

}
