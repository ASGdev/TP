package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;

import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class TestProdCons  extends Simulateur {
	

	public TestProdCons(Observateur observateur) {
		super(observateur);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestProdCons(new Observateur()).start();
	}

	@Override
	protected void run() throws Exception {
		// TODO Auto-generated method stub
			Properties properties = new Properties();
			//remplacer file par le configuration xml
			//properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
			String key;
			int value;
			Class<?> thisOne = getClass();
			for(Map.Entry<Object,Object> entry : properties.entrySet()) {
				key = (String)entry.getKey();
				value = Integer.parseInt((String)entry.getValue());
				thisOne.getDeclaredField(key).set(this,value);
			}
			
			System.out.println("Main lanc� :\n");
		
	}
}
