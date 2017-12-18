package jus.poc.prodcons.v3;
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
			try {
				addMessage(production());
			} catch (ControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
	
	
	
	
	private Message production() throws ControlException{
		MessageX m = new MessageX(this.msg_send, this.identification(), " ProdCons");
		int delay = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.observateur.productionMessage(this, m, delay);
		return m;
		
	}
	
	public String name(){
		return this.getName()+" N "+this.identification();
	}

}
