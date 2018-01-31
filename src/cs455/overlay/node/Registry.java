package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Registry Node
 *
 * @author Adam Bellendir
 *
 */
public class Registry implements Node {
	
	private int registryPortNumber;
	private ServerSocket serverSocket;
	private Socket[] nodeSockets = new Socket[128];
	
	/**
	 * @throws IOException 
	 * 
	 */
	public Registry(int registryPortNumber) {
		this.registryPortNumber = registryPortNumber; 
		try {
			serverSocket = openServerSocket(registryPortNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
     * @param PortNumber
     * @throws IOException
     */
	public ServerSocket openServerSocket(int PortNumber) throws IOException {
		return new ServerSocket(PortNumber);
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		Registry registry = new Registry(Integer.parseInt(args[1]));
	}
}
