package jus.aor.nio.v3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author morat 
 */
public class ReadCont  extends Continuation{

	SocketChannel sock;
	ByteBuffer lenBuf = ByteBuffer.allocate(4); // for reading the length of a message
	ByteBuffer msgBuf = null; // for reading a message
	static final int READING_LENGTH = 1;
	static final int READING_MSG = 2;
	int currentState = READING_LENGTH; // initial state 

	/**
	 * @param sc
	 */
	public ReadCont(SocketChannel sc){
		super(sc);
	}
	/**
	 * @return the message
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	protected Message handleRead() throws IOException, ClassNotFoundException{
	// todo
		int state;
		int length;
		if (state == READING_LENGTH){
			int nb = sock.read(lenBuf);

			if (lenBuf.remaining() == 0) {
				length = bytesToInt(lenBuf);
				msgBuf = ByteBuffer.allocate(bytesToInt(lenBuf));
				lenBuf.position(0);
				state = READING_MSG;
			}
		}
		if (state == READING_MSG) {
			 
			if (msgBuf.remaining() == 0){ // the message has been fully received
				deliver(msgBuf.array()); // deliver it
				msgBuf = null;
			}
			else {
			}
		}
	}
}

