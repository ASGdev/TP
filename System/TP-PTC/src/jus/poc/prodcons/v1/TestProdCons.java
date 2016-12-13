package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;


import com.sun.org.apache.xml.internal.security.Init;

public class TestProdCons extends Simulateur {

	public TestProdCons(Observateur observateur){super(observateur);}
	
	protected void run() throws Exception{
		while(true){
			System.out.println("1");
		}
		// le corps de votre programme principalz
		
		//PASSAGE DES OPTIONS
		
		//INIT MESSAGE 
		
		//INIT DES THREADS
		
		//SIMULATION
		//ProdCons ProdCons = new ProdCons(3,3,this.observateur);
		
	}
	
	
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();//n'hérite pas de thread, ca sert a rien...
		

		Observateur observateur = new Observateur();
		ProdCons ProdCons = new ProdCons(10,0,observateur);
		
		
	}
	
	
	
	
	


}

