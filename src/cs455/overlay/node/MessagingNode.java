/**
 * 
 */
package cs455.overlay.node;

/**
 * @author Adam Bellendir
 *
 */
public class MessagingNode implements Node {
	
	private int sendTracker = 0;
	private int receiveTracker = 0;

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
	 * @param args
	 */
	public static void main(String [] args) {
		String registryHost = args[1];
		String registryPortNumber = args[2];
	}
}
