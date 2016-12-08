package jus.poc.prodcons;

public abstract class Acteur implements _Acteur{

	private static int consommateurIdentification;
	protected int deviationTempsDeTraitement;
	private int identification;
	protected int moyenneTempsDeTraitement;
	protected Observateur observateur;
	private static int producteurIdentfication;
	protected static int typeConsommateur;
	protected static int typeProducteur;
	
	protected Acteur(
			int type, Observateur observateur,
			int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement)
	{
		
	};
	
	public int deviationTempsDeTraitement() {
		return 0;
	};
    //renvoie l'écart type du temps moyen de traitement 
	public int identification() {
		return 0;
	};
    //renvoie l'identification de l'acteur 
	public int moyenneTempsDeTraitement(){
		return 0;
	}; 
    //renvoie le temps moyen de traitement 
	public int nombreDeMessages(){
		return 0;
	};
    //renvoie le nombre de messages (à) traités(er) 

}

