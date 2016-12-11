package jus.poc.test;

import java.util.Random;

public class Test {

	public static void main(String[] args){
		Aleatoire randomizeur = new Aleatoire(2,10);
		int valeur = Aleatoire.next();
		int val = Aleatoire.valeur(2,10);
		int [] v = Aleatoire.valeurs(10,2,10);
		System.out.println(valeur);
		System.out.println(val);
		for (int i = 0; i < v.length; i++) {
			System.out.println(v[i]);
		}
	};
}
