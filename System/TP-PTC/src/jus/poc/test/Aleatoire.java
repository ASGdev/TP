package jus.poc.test;

import java.util.Random;

public class Aleatoire {
	protected static int borneInf=0;
	protected static int borneSup=2;
	protected static int moyenne;
	protected static int deviation;
	protected static Random var = new Random();
	
	Aleatoire(int moyenne, int deviation){
			this.moyenne=moyenne;
			this.deviation=deviation;
		};
	
	static int next(){
		double nextGaussianValue = var.nextGaussian() * deviation + moyenne;
		return (int)nextGaussianValue;
	}
	
	static int valeur(int moyenne, int deviation){
		int plage = borneSup-borneInf;
		int nextValeurValue=var.nextInt(plage)+borneInf;
		return nextValeurValue;
	}
	
	static int[] valeurs(int size,int moyenne, int deviation){
		int[] tab = new int[size];
		for (int i = 0; i < tab.length; i++) {
			tab[i] = next();
		}
		return tab;
	}
	
	
}
