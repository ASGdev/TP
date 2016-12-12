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
	
	
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();
		ProdCons ProdCons = new ProdCons(3,3, new Observateur());
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

