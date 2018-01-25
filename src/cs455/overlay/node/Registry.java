package cs455.overlay.node;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * Registry Node
 *
 * @author Adam Bellendir
 *
 */
public class Registry implements Node {
	
	private int registryPortNumber;
	private static ServerSocket serverSocket;

	
	/**
	 * 
	 */
	public Registry(){
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	private void listMessagingNodes() {
		
	}
	
	/**
	 * 
	 */
	private void setupOverLayNumberOfRoutingTableEnties(){
		
	}
	
	/**
	 * 
	 */
	private void listRoutingTables() {
		
	}
	
	/**
	 * 
	 * @param numberOfMessages
	 */
	private void start(int numberOfMessages) {
		
	}

    /**
     *
     */
	public void run() {
		
	}

    /**
     *
     * @param PortNumber
     * @throws IOException
     */
	public static void openServerSocket(int PortNumber) throws IOException {
		serverSocket = new ServerSocket(PortNumber);
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		int registryPortNumber = Integer.parseInt(args[1]);
		try {
			openServerSocket(registryPortNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
