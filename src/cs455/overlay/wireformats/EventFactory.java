/**
 * 
 */
package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import cs455.overlay.node.Node;
import cs455.overlay.transport.IncomingMessage;

/**
 * 
 * Bridge between nodes
 * @author Chemical
 *
 */
public class EventFactory implements Protocol,Runnable {

	private static final EventFactory eventFactory = new EventFactory();
	private volatile boolean TERMINATE = false;
	private Node node;
	private final BlockingQueue<IncomingMessage> buffer = new ArrayBlockingQueue<IncomingMessage>(1000);

	/**
	 * 
	 */
	private EventFactory() {
	}

	private synchronized void setTermination() {
		TERMINATE = true;
	}

	/**
	 *
	 * @return
	 */
	public static EventFactory getInstance() {
		return eventFactory;
	}
	
	public void addMessage(IncomingMessage message) {
		try {
			buffer.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				node.setSocketPort(port);
				node.setSocketAddress(IP);
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

	@Override
	public void run() {
		while(!TERMINATE) {
			try {
				IncomingMessage message = buffer.take();
				handleBytes(message.getMarshalledBytes(),message.getIP_expected(),message.getConnectionPortNumber());
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
