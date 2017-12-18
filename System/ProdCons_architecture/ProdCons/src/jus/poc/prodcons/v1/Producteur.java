package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {
	int msg_toSend;
	int msg_send;
	String message;
	ProdCons tampon;
	


	protected Producteur(Observateur observateur,
			int tempsMoyenProduction,
			int deviationTemps,
			ProdCons tampon,
			int msgRemain)
					throws ControlException {

		super(1, observateur, tempsMoyenProduction, deviationTemps);

		//nbr de message à produire
		msg_send=0;
		msg_toSend = msgRemain;
		this.tampon = tampon;
	}

	@Override
	public int nombreDeMessages() {
		return msg_send;
	}

	@Override
	public void run() {
		while(this.msg_toSend - msg_send >0){			
			addMessage(production());
			
		}
	}
	
	private void addMessage(Message m){
		try {
			tampon.put(this,m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg_send +=1;		
	}
	
	
	
	
	private Message production(){
		MessageX m = new MessageX(this.msg_send, this.identification(), " prodcons ");
		try {
			Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m;
		
	}
	
	public String name(){
		return this.getName()+""+this.identification();
	}

}
