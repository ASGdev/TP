package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {
	int msg_toSend;
	int msg_send;
	String message;
	ProdCons tampon;
	Aleatoire rand;
	


	protected Producteur(Observateur observateur,
			int tempsMoyenProduction,
			int deviationTemps,
			ProdCons tampon,
			int msgRemain)
					throws ControlException {

		super(1, observateur, tempsMoyenProduction, deviationTemps);

		rand = new Aleatoire(tempsMoyenProduction, deviationTemps);
		//nbr de message Ã  produire
		msg_send=0;
		msg_toSend = msgRemain;
		this.tampon = tampon;
	}

	@Override
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run() {
		while(this.msg_toSend - msg_send >0){			
			addMessage(production());
			
		}
	}
	
	private void addMessage(Message m){
		System.out.println("Demande d'accee de "+name()+"");
		try {
			tampon.put(this,m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg_send +=1;
		System.out.println("Tampon libre:"+tampon.taille()+" avec l'ajout de "+name()+", reste "+msg_send+"a traiter");
		System.out.println("Sortie d'accee de "+name()+"");	
		
	}
	
	
	
	
	private Message production(){
		MessageX m = new MessageX(this.msg_send, this.identification(), "Yolo");
		try {
			Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Traitement de production du thread "+name());

		return m;
		
	}
	
	public String name(){
		return this.getName()+"N°"+this.identification();
	}

}
