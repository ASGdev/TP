package jus.poc.prodcons.v3;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {
	int msg_toSend;
	int msg_send;
	String message;
	ProdCons tampon;
	
	Observateur observateur;

	protected Producteur(Observateur observateur,
			int tempsMoyenProduction,
			int deviationTemps,
			ProdCons tampon,
			int msgRemain)
					throws ControlException {

		super(1, observateur, tempsMoyenProduction, deviationTemps);
		this.observateur = observateur;
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
			try {
				addMessage(production());
			} catch (ControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		System.out.println("Tampon libre:"+tampon.taille()+" avec l'ajout de "+name()+", reste "+(msg_toSend-msg_send)+"a traiter");
		System.out.println("Sortie d'accee de "+name()+"");	
		
	}
	
	
	
	
	private Message production() throws ControlException{
		MessageX m = new MessageX(this.msg_send, this.identification(), "Yolo");
		int delay = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		observateur.productionMessage(this, m, delay);
		System.out.println("Traitement de production du thread "+name());

		return m;
		
	}
	
	public String name(){
		return this.getName()+" N "+this.identification();
	}

}
