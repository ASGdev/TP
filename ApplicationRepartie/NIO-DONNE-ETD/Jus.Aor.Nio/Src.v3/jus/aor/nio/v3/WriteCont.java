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
	
	ArrayList<ByteBuffer> messages = new ArrayList<ByteBuffer>() ; // messages to send
	ByteBuffer lenBuf = ByteBuffer.allocate(4); // for writing the length of a message
	ByteBuffer msgBuf = null; // for writing a message
	static final int WRITING_LENGTH = 1;
	static final int WRITING_MSG = 2;
	static final int WRITING_DONE = 3;
	int currentState = WRITING_LENGTH; // initial state
	int state;
	SelectionKey key;
	
	protected WriteCont(SelectionKey selectionKey, SocketChannel sc) {
		super(sc);
		key = selectionKey;
		state = currentState;
	}
	

	void handleWrite() throws IOException {
		int nb =0;
		if (state == WRITING_LENGTH){
			if (lenBuf.remaining() == 0) {
				state = WRITING_MSG;
			}
		} else if (state == WRITING_MSG) {
			if (msgBuf.remaining() > 0){
				nb = socketChannel.write(msgBuf);

			}
			if (msgBuf.remaining() == 0){ // the message has been fully sent
				if (! messages.isEmpty()){
					msgBuf = messages.remove(0);
					lenBuf.position(0); lenBuf.putInt(0, msgBuf.remaining());
					state = WRITING_LENGTH;
				} else state = WRITING_DONE;
			}
		}
	}


	private void write(ByteBuffer msg) {
		messages.add(msg);
		if (state == WRITING_DONE)
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}
}