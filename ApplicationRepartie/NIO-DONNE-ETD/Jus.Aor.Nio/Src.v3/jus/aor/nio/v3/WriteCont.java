package jus.aor.nio.v3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author morat 
 */
public class WriteCont extends Continuation{
	private SelectionKey key;
	// state automata
	private enum State{WRITING_DONE, WRITING_LENGTH,WRITING_DATA;}
	// initial state
	protected State state = State.WRITING_DONE;
	// the list of bytes messages to write
	protected ArrayList<byte[]> msgs = new ArrayList<>() ;
	// buf contains the byte array that is currently written
	protected ByteBuffer buf = null;
	protected ByteBuffer lenBuf = ByteBuffer.allocate(4);
	SocketChannel sock;


	/**
	 * @param k
	 * @param sc
	 */
	public WriteCont(SelectionKey k,SocketChannel sc){
		super(sc);
		key = k;
	}


	/**
	 * @return true if the msgs are not completly write.
	 */
	protected boolean isPendingMsg(){
	// todo
		return true;
	}


	/**
	 * @param data
	 * @throws IOException 
	 */
	protected void sendMsg(Message data) throws IOException{
	// todo	
		Message.add(data);
		if (state == State.WRITING_DONE){
		key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	/**
	 * @throws IOException
	 */
	protected void handleWrite()throws IOException{
	// todo
		if (state == State.WRITING_LENGTH){

			 if (lenBuf.remaining() == 0) {
				 state = State.WRITING_DATA;
			 }
		} else
		if (state == State.WRITING_DATA) {
			if (buf.remaining() > 0){
				int nb = sock.write(buf);
			}
			if (buf.remaining() == 0){ // the message has been fully sent
				if (! Message.isEmpty()){
					buf = Message.remove(0);
					lenBuf.position(0); lenBuf.putInt(0, buf.remaining());
					state = State.WRITING_LENGTH;
				}
				else state = State.WRITING_DONE;
		
			}
}
}
}