/**
 * 
 */
package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import cs455.overlay.node.Node;

/**
 * 
 * Bridge between nodes
 * @author Chemical
 *
 */
public class EventFactory {

	private static final EventFactory eventFactory = new EventFactory();
	private Node node;

	/**
	 * 
	 */
	private EventFactory() {
	}

	/**
	 *
	 * @return
	 */
	public static EventFactory getInstance() {
		return eventFactory;
	}
	
	/**
	 * 
	 * @param marshalledBytes
	 * @throws IOException
	 */
	public void handleBytes(byte[] marshalledBytes) throws IOException{
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		int type = din.readByte();
		System.out.println(type);
		try {
			System.out.println("Entering try block");
			node.onEvent(Protocol.getEvent(type,marshalledBytes));
			System.out.println("Event was created");
		}catch(IOException e) {
			System.out.println("Failed to create \"Event\" class");
			
		}
		baInputStream.close();
		din.close();
	}
	
	public void giveNode(Node node) {
		this.node = node;
	}

}
