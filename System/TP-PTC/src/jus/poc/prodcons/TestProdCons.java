package jus.poc.prodcons;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

public class TestProdCons extends Simulateur {

	public TestProdCons(Observateur observateur){super(observateur);}
	
	protected void run() throws Exception{
		// le corps de votre programme principalz
		
		//PASSAGE DES OPTIONS
		
		//INIT MESSAGE 
		
		//INIT DES THREADS
		
		//SIMULATION
		//ProdCons ProdCons = new ProdCons(3,3,this.observateur);
		
	}
	
	
	public static void main(String[] args){new TestProdCons(new Observateur()).start();
		Observateur observateur = new Observateur();
		ProdCons ProdCons = new ProdCons(3,3,observateur);
		
		int consultation =1;
		System.out.println("Voulez vous acc�dez � un journal d'�venements ? \n"
				+ "non (0)/ oui(1)");
		try {
				consultation = System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(consultation != 0){
			System.out.println("Que voulez vous consultez ? \n "
					+ "1: La liste des consommateurs \n"
					+ "2: La liste des producteurs \n"
					+ "3: La liste des messages produits \n"
					+ "4: La liste des messages consomm�s \n"
					+ "5: La liste des messages d�pos�s \n"
					+ "6: La liste des messages retir�s ");
			int choix = -1;
			try {
				choix = System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch (choix) {
			case 1:
				for (int i = 0; i < observateur.consommateurs.size(); i++) {
					System.out.println(observateur.consommateurs.get(i));
				}
				break;
			case 2:
				for (int i = 0; i < observateur.producteurs.size(); i++) {
					System.out.println(observateur.producteurs.get(i));
				}
				break;
			case 3:
				for (int i = 0; i < observateur.messagesProduits.size(); i++) {
					System.out.println(observateur.messagesProduits.get(i));
				}
				break;
			case 4:
				for (int i = 0; i < observateur.messagesConsommes.size(); i++) {
					System.out.println(observateur.messagesConsommes.get(i));
				}
				break;
			case 5:
				for (int i = 0; i < observateur.depot.size(); i++) {
					System.out.println(observateur.depot.get(i));
				}
				break;
			case 6:
				for (int i = 0; i < observateur.messagesRetires.size(); i++) {
					System.out.println(observateur.messagesRetires.get(i));
				}
				break;
			default:
				System.out.println("Vous n'avez pas entrer une option valide (entre 1 et 6)");
				break;
			}
			System.out.println("Voulez vous accedez a un journal d'evenements ? \n"
					+ "oui (0) / non (1)");
			try {
				consultation = System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	
	
	
	protected void initOption(String file) throws InvalidPropertiesFormatException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Properties properties = new Properties();
		properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
		String key;
		int value;
		Class<?> thisOne = getClass();
		for(Map.Entry<Object,Object> entry : properties.entrySet()) {
			key = (String)entry.getKey();
			value = Integer.parseInt((String)entry.getValue());
			thisOne.getDeclaredField(key).set(this,value);
		}
		options = properties;
	}


}

