package jus.poc.prodcons.v1;

public class MsgInteger implements Message {
	private int value;
	public MsgInteger(int x){
		value = x;
	}
	@Override
	public Object getContent() {
		return value;
	}

}
