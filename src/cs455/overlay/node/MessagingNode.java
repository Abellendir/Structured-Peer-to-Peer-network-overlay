/**
 * 
 */
package cs455.overlay.node;

import java.io.IOException;
import java.net.ServerSocket;

import cs455.overlay.util.InteractiveCommandParser;
import cs455.overlay.wireformats.Event;

/**
 * @author Adam Bellendir
 *
 */
public class MessagingNode implements Node {
	
	private int nodeID;
	private int sendTracker = 0;
	private int receiveTracker = 0;
	private int relayedTracker = 0;
	private long sendSummation = 0;
	private long receiveSummation = 0;
	
	/**
	 * 
	 */
	public MessagingNode() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	private void printCountersandDiagnostics() {
		
	}
	
	/**
	 * 
	 */
	private void exitOverlay() {
		
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private ServerSocket openServerSocket() throws IOException{
		ServerSocket serverSocket = new ServerSocket(0);
		return serverSocket;
	}

	/**
	 * @param event
	 */
	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		String registryHost = args[1];
		String registryPortNumber = args[2];
		MessagingNode mNode = new MessagingNode();
		InteractiveCommandParser interactiveCommandParser = new InteractiveCommandParser(mNode);
		Thread commandParser = new Thread(interactiveCommandParser);
		commandParser.start();
		while(true) {
			
		}
	}

}
