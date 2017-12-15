package jus.poc.prodcons;

public class MessageX implements Message{
	int msg;
	
	public MessageX(int i) {
		super();
		msg = i;
	}
	
	public String toString() {
		return ""+msg;
	}
}
