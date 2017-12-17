package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;
public class MessageX implements Message{
	public int numMsg;
	public int numProd;
	public String id;
	public String contentMsg = new String();
	
	public MessageX(int numMsg, int numProd, String contentMsg) {
		super();
		this.numMsg = numMsg;
		this.numProd = numProd;
		id = ""+numMsg+numProd+numProd;
		this.contentMsg = contentMsg;
	}
	
	public String toString() {
		return "Message : "+numMsg+" <- Producteur "+numProd;
	}
}
