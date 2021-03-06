package jus.poc.prodcons.v2;

public abstract class Acteur extends Thread implements _Acteur {	
	private static int compt = 0;
	protected int deviationTempsDeTraitement;
	private int identification;
	protected int moyenneTempsDeTraitement;
	protected Observateur observateur;
	protected static int typeConsommateur = 2;
	protected static int typeProducteur = 1;
	protected int type;
	protected int nbMessage;

	protected Acteur(
			int type, Observateur observateur,
			int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement)
	{
		super("Thread_type_"+type);
		this.type = type;
		this.observateur = observateur;
		this.moyenneTempsDeTraitement = moyenneTempsDeTraitement;
		this.deviationTempsDeTraitement = deviationTempsDeTraitement;
		identification = compt;
		compt ++;
		nbMessage = 6; //use option to configure it
	};
	
	public int deviationTempsDeTraitement() {
		return deviationTempsDeTraitement;
	};
    //renvoie l'ecart type du temps moyen de traitement 
	public int identification() {
		return identification;
	};
    //renvoie l'identification de l'acteur 
	public int moyenneTempsDeTraitement(){
		return moyenneTempsDeTraitement;
	}; 
    //renvoie le temps moyen de traitement 
	public int nombreDeMessages(){
		return nbMessage;
	};
    //renvoie le nombre de messages a traiter
	
}

