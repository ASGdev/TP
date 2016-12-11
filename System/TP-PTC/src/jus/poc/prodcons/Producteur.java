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
			MsgInteger m = new MsgInteger(this.nbMessage);
			addMessage(m);
			/*try {
				//this.sleep(Aleatoire.next());
				//this.wait();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
		
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
		System.out.println("tampon libre :"+tampon.taille()+" avec l'ajout du thread "+this.getName()+"N°"+this.identification());
		this.nbMessage -= 1;
		notify();		
	}
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}

}
