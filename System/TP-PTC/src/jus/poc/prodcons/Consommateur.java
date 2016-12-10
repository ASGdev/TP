package jus.poc.prodcons;

public class Consommateur extends Acteur implements _Consommateur {
	
	public Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
	}
	
	public void run(){
		for(int i =0;i<10;i++){
			System.out.println(this.getName()+"N°"+this.identification());
		}
	}
	

}
