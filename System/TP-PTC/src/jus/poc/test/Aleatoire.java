package jus.poc.test;

import java.util.Random;

public class Aleatoire {
	protected static int borneInf;
	protected static int borneSup;
	protected static int moyenne;
	protected static int deviation;
	protected static Random var = new Random();
	
	Aleatoire(int moyenne, int deviation){
			this.moyenne=moyenne;
			this.deviation=deviation;
		};
	
	static int next(){
		double nextGaussianValue = var.nextGaussian() * deviation + moyenne;
		return 0;
	}
	
	static int valeur(int moyenne, int deviation){
		int nextValeurValue=var.nextInt(borneSup-borneInf)+borneInf;
		return 0;
	}
	
	static int[] valeurs(int size,int moyenne, int deviation){
		int[] tab = new int[size];
		for (int i = 0; i < tab.length; i++) {
			tab[i] = next();
		}
		return tab;
	}
	
	
}
