package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;


import java.util.Properties;
import java.util.Vector;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class TestProdCons extends Simulateur {
 
    static String option; // fichier option
    //Données du xml
    int nbProd,nbCons,nbBuffer,
    	nombreMoyenDeProduction,deviationNombreMoyenDeProduction,
    	nombreMoyenDeConsommation,deviationNombreMoyenDeConsommation,
    	tempsMoyenProduction,deviationTempsMoyenProduction,
    	tempsMoyenConsommation,deviationTempsMoyenConsommation;
    
    protected ProdCons tampon;
    Aleatoire random;
    Vector<Producteur> prod;
    Vector<Consommateur> cons;

    protected void init(String file) {
        Properties properties = new Properties();
        try {
                properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
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
        


    }

    public TestProdCons(Observateur observateur){super(observateur);}
    
    
    protected void run() throws Exception{
    	//appel fichier xml
        init("./jus/poc/prodcons/option/"+option);
   
        //Déclaration new buffer
        tampon = new ProdCons(nbBuffer);
        prod = new Vector<Producteur>();
        cons = new Vector<Consommateur>();
        
        // on génère une variable aléatoire
        random = new Aleatoire(tempsMoyenProduction,deviationTempsMoyenProduction);
        
        //On utilise les données du xml pour générer les consommateur, producteur etc
        //Cons : nbr de message a lire aléatoire
       
        for(int i = 0 ; i < nbProd ; i++){
        	Producteur p = new Producteur(new Observateur(), tempsMoyenProduction, deviationTempsMoyenProduction, tampon, random.next());
        	prod.add(p);
        	p.start();
            
        }
       
        /* on génère les consommateurs */
        for(int i = 0 ; i < nbCons ; i++){
        	Consommateur p = new Consommateur(new Observateur(), tempsMoyenConsommation, deviationTempsMoyenConsommation,tampon,random.next());
           cons.add(p);
           p.start();
        }
        

    }
    
    
    public static void main(String[] args){
    	//Un nom de fichier est passé en paramètre
    	option = new String();
    	if(args.length == 1){
    		option = args[0];
    	}
    	else{
    		option = "v1.xml";
    	}
    	
    	new TestProdCons(new Observateur()).start();
    	}
    
    
}