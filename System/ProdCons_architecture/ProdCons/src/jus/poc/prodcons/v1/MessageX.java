package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;
public class MessageX implements Message{
	public int numMsg;
	public int numProd;
	public String contentMsg = new String();
	
	public MessageX(int numMsg, int numProd) {
		super();
		this.numMsg = numMsg;
		this.numProd = numProd;
	}
	
	public String toString() {
		return ""+numMsg;
	}
}
