<<<<<<< Updated upstream
package jus.poc.prodcons;

public abstract class Acteur {

}
=======
package jus.poc.prodcons;

public abstract class Acteur implements Acteur{

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
	
	int deviationTempsDeTraitement() {};
    //renvoie l'écart type du temps moyen de traitement 
	int identification() {};
    //renvoie l'identification de l'acteur 
	int moyenneTempsDeTraitement(){}; 
    //renvoie le temps moyen de traitement 
	abstract int nombreDeMessages();
    //renvoie le nombre de messages (à) traités(er) 

}
>>>>>>> Stashed changes
