package jus.poc.prodcons;

import java.util.Random;

public class Aleatoire {
	protected int borneInf;
	protected int borneSup;
	protected int moyenne;
	protected int deviation;
	protected Random var;
	
	Aleatoire(int moyenne, int deviation){
			this.moyenne=moyenne;
			this.deviation=deviation;
		};
	
	int next(){
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
			tab[i] = var.next();
		}
		return tab;
	}
	
	
}
