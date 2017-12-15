package jus.poc.prodcons.v2;
import jus.poc.prodcons.*;



public class MessageX implements Message {
	private int identProd;
	private int numMess;
	private boolean error = false;
	private String desc = new String();
	
	public MessageX(int identProd,int numMess){
		this.identProd = identProd;
		this.numMess = numMess;
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
			return "message: "+numMess+"; producteur: "+identProd;
	}
	
}
