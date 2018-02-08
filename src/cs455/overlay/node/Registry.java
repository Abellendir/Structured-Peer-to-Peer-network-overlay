package cs455.overlay.node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cs455.overlay.transport.TCPConnectionsCache;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.util.InteractiveCommandParser;
import cs455.overlay.wireformats.Event;

/**
 *
 * Registry Node
 *
 * @author Adam Bellendir
 *
 */
public class Registry implements Node {
	
	private String[][] nodes;
	private Thread serverThread = null;
	private Thread interactiveCommandParser = null;
	private int registryPortNumber;
	private ServerSocket serverSocket;
	private Socket[] nodeSockets = new Socket[128];
	private TCPConnectionsCache tcpConnectionsCache = TCPConnectionsCache.getInstance();
	
	/**
	 * @throws IOException 
	 * 
	 */
	public Registry(int registryPortNumber) {
		this.registryPortNumber = registryPortNumber;
		InteractiveCommandParser commandParser = new InteractiveCommandParser(this);
		Thread interactiveCommandParser = new Thread(commandParser);
		interactiveCommandParser.start();
		this.interactiveCommandParser = interactiveCommandParser;
		try {
			serverThread = startServerThread(registryPortNumber);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	@Override
	public void onEvent(Event event) {
		
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
     * @return 
     * @throws IOException
     */
	public Thread startServerThread(int portNumber) throws IOException {
		ServerSocket serverSocket = new ServerSocket(portNumber);
		TCPServerThread tcpServerThread = new TCPServerThread(serverSocket);
		Thread serverThread = new Thread(tcpServerThread);
		serverThread.start();
		return serverThread;
	}
	
	/**
	 * 
	 */
	public void closeServerSocket() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		Registry registry = new Registry(Integer.parseInt(args[1]));
	}
}
