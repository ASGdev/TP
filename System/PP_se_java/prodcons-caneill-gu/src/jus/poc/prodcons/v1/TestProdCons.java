package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;


import java.util.ArrayList;
import java.util.Properties;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class TestProdCons extends Simulateur {
    private ProdCons ress; /* les ressources */
    private Display display;
    
    static String fileo; // fichier option

    int nbProd,nbCons,nbBuffer,
    	nombreMoyenDeProduction,deviationNombreMoyenDeProduction,
    	nombreMoyenDeConsommation,deviationNombreMoyenDeConsommation,
    	tempsMoyenProduction,deviationTempsMoyenProduction,
    	tempsMoyenConsommation,deviationTempsMoyenConsommation;
    
    boolean bdisplay;
    
    Aleatoire rand;
    
    ArrayList<Producteur> prod;
    ArrayList<Consommateur> cons;

    protected void init(String file) {
        Properties properties = new Properties();
        try {
                properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
        }
        catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
        }
        catch (IOException e) {
                e.printStackTrace();
        }
        nbProd= Integer.parseInt(properties.getProperty("nbProd"));
        nbCons= Integer.parseInt(properties.getProperty("nbCons"));
        nbBuffer= Integer.parseInt(properties.getProperty("nbBuffer"));
        tempsMoyenProduction=Integer.parseInt(properties.getProperty("tempsMoyenProduction"));
        deviationTempsMoyenProduction=Integer.parseInt(properties.getProperty("deviationTempsMoyenProduction"));
        tempsMoyenConsommation=Integer.parseInt(properties.getProperty("tempsMoyenConsommation"));
        deviationTempsMoyenConsommation=Integer.parseInt(properties.getProperty("deviationTempsMoyenConsommation"));
        nombreMoyenDeProduction=Integer.parseInt(properties.getProperty("nombreMoyenDeProduction"));
        deviationNombreMoyenDeProduction=Integer.parseInt(properties.getProperty("deviationNombreMoyenDeProduction"));
        
        bdisplay=Boolean.parseBoolean(properties.getProperty("display"));


    }

    public TestProdCons(Observateur observateur){super(observateur);}
    
    
    protected void run() throws Exception{
    	/* récupère les options */
        init("./jus/poc/prodcons/options/"+fileo);
    	//init("./jus/poc/prodcons/options/options.xml");
        
        /* gère l'affichage */
        display = new Display(bdisplay);
        
        /* producteurs, consommateurs */
        prod = new ArrayList<Producteur>();
        cons = new ArrayList<Consommateur>();
        
        /* ressources */
        ress = new ProdCons(nbBuffer, display);
        
        /* aléa */
        rand = new Aleatoire(nombreMoyenDeProduction, deviationNombreMoyenDeProduction);
        
        /* on génère les producteurs */
        for(int i = 0 ; i < nbProd ; i++){
             prod.add(new Producteur(new Observateur(), tempsMoyenProduction, deviationTempsMoyenProduction, ress, rand.next()));
             prod.get(prod.size()-1).start();
        }
        
        /* on génère les consommateurs */
        for(int i = 0 ; i < nbCons ; i++){
            cons.add(new Consommateur(new Observateur(), tempsMoyenConsommation, deviationTempsMoyenConsommation, ress));
            cons.get(cons.size()-1).start();
        }
        
        /* on attent la fin de l'exécution des producteurs */
        for(Producteur p: prod){
            p.join();
        }
        
        /* on attent que tous les messages aient été consommés */
        while(!ress.consumed()){
        	;
        }
        
        /* on dit aux consommateurs qu'il n'y a plus rien à faire */
        for(Consommateur c: cons){
            c.stopMe();
            //c.notify();
        }
        
        ress.stopMe();

    }
    
    
    public static void main(String[] args){
    	fileo = new String();
    	if(args.length == 1){
    		fileo = args[0];
    	}
    	else{
    		fileo = "options.xml";
    	}
    	//for(String fileo:args)
    	//	System.out.println(fileo);
    	
    	new TestProdCons(new Observateur()).start();
    	}
    
    
}
