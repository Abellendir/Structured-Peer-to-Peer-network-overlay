/**
 * 
 */
package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

import cs455.overlay.node.Node;

/**
 * 
 * Bridge between nodes
 * @author Chemical
 *
 */
public class EventFactory implements Protocol {

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
	 * @param port 
	 * @throws IOException
	 */
	public void handleBytes(byte[] marshalledBytes, byte[] IP, int port) throws IOException{
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		int type = din.readByte();
		try {
			Event event = getEvent(type,marshalledBytes);
			if(event instanceof OverlayNodeSendsRegistration) {
				OverlayNodeSendsRegistration nodeTemp = (OverlayNodeSendsRegistration) event;
				nodeTemp.setSocketPort(port);
				nodeTemp.setSocketAddress(IP);
				if(!verifyAddress(nodeTemp.getIP_address(), IP)) {
					nodeTemp.setStatus();
				}
				event = nodeTemp;
			}
			if(event instanceof OverlayNodeSendsDeregistration) {
				OverlayNodeSendsDeregistration node = (OverlayNodeSendsDeregistration) event;
				if(!verifyAddress(node.getIP_address(),IP)) {
					node.setStatus();
				}
			}
			node.onEvent(event);
		}catch(IOException e) {
			System.out.println("Failed to create \"Event\" class");
			
		}
		baInputStream.close();
		din.close();
	}
	

	/**
	 * 
	 * @param node
	 */
	public void giveNode(Node node) {
		this.node = node;
	}
	
	/**
	 * Determines the type of event being received and to send to onEvent
	 * @param type
	 * @param marshalledBytes
	 * @return
	 * @throws IOException 
	 */
	
	private boolean verifyAddress(byte[] actual, byte[] expected) {
		return Arrays.equals(actual, expected);
	}
}
