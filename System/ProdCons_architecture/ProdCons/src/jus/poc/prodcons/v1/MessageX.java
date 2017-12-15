package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;
public class MessageX implements Message{
	public int msg;
	
	public MessageX(int i) {
		super();
		msg = i;
	}
	
	public String toString() {
		return ""+msg;
	}
}
