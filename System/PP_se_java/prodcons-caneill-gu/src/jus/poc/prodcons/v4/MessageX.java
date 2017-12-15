package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;



public class MessageX implements Message {
	private int identProd;
	private int numMess;
	private boolean error = false;
	private String desc = new String();
	
	private int nbExCourant;
	private int nbExMax;
	
	public MessageX(int identProd,int numMess, int nbEx){
		this.identProd = identProd;
		this.numMess = numMess;
		this.nbExCourant = nbEx;
		this.nbExMax = nbEx;
	}
	
	public MessageX(String desc){
		error = true;
		this.desc = desc;
	}
	
	public String toString(){
		if(error){
			return desc;
		}
		else
			return "message: "+numMess+"("+(nbExMax - nbExCourant + 1)+"/"+nbExMax+"); producteur: "+identProd;
	}
	
}
