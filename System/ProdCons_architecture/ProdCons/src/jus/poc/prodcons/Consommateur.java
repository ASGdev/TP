package jus.poc.prodcons;

public class Consommateur extends Acteur implements _Consommateur {
	int nbMessage =0;

	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement,int nbMessage) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deviationTempsDeTraitement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int identification() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moyenneTempsDeTraitement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run() {
		while(this.nbMessage > 0){			
			treatment(getMessage());
		}
	}

}
