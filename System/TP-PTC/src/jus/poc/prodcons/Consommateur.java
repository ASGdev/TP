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
		for(int i =0;i<10;i++){
			System.out.println(this.getName()+"N°"+this.identification());
		}
	}
	

}
