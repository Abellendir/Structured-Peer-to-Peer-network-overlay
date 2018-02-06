/**
 * 
 */
package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * 
 * Bridge between nodes
 * @author Chemical
 *
 */
public class EventFactory {

	private static EventFactory eventFactory = null;
	private Protocol protocol;

	/**
	 * 
	 */
	private EventFactory() {
		protocol = new Protocol();
	}

	/**
	 *
	 * @return
	 */
	public static EventFactory getInstance() {

		if(eventFactory == null){
			eventFactory = new EventFactory();
		}

		return eventFactory;
	}
	
	public Event getType(byte[] marshalledBytes) throws IOException{
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		byte type = din.readByte();
		return protocol.getEvent(type,marshalledBytes);
	}

}
