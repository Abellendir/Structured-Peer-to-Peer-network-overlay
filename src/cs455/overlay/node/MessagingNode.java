/**
 * 
 */
package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import cs455.overlay.routing.RoutingTable;
import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPConnectionsCache;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.util.InteractiveCommandParser;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.OverlayNodeSendsRegistration;

/**
 * @author Adam Bellendir
 *
 */
public class MessagingNode implements Node {
	
	private int nodeID;
	private RoutingTable routingTable;
	private TCPConnectionsCache cache = TCPConnectionsCache.getInstance();
	private EventFactory eventFactory = EventFactory.getInstance();
	private byte[] IP_address;
	private int portNumber;
	private ServerSocket serverSocket;
	private int sendTracker = 0;
	private int receiveTracker = 0;
	private int relayedTracker = 0;
	private long sendSummation = 0;
	private long receiveSummation = 0;
	
	/**
	 * 
	 */
	public MessagingNode(String hostName, int portNumber){
		try {
			IP_address = InetAddress.getLocalHost().getAddress();
			openServerSocket();
			eventFactory.giveNode(this);
			registerWithRegistry(hostName, portNumber);
		} catch (UnknownHostException e) {
			System.out.println("\nFailed to connect with registry node");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEvent(Event event) {
		if(event.getType() == 3) {
			System.out.println("Registered: test");
		}
		
	}
	
	public void start() {
		
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
	private void openServerSocket() throws IOException{
		serverSocket = new ServerSocket(0);
		TCPServerThread serverThread = new TCPServerThread(serverSocket);
		Thread server = new Thread(serverThread);
		server.start();
		cache.addServerConnection(server);
	}
	
	//FIGURE OUT IP ADDRESS
	private void registerWithRegistry(String hostName, int portNumber) throws IOException{
		Socket socket = new Socket(hostName,portNumber);
		InetAddress addr = socket.getLocalAddress();
		byte[] address = addr.getAddress();
		TCPConnection registryConnection = new TCPConnection(socket);
		Thread registry = new Thread(registryConnection);
		registry.start();
		cache.addRegistry(registryConnection);
		OverlayNodeSendsRegistration registration = 
				new OverlayNodeSendsRegistration(address.length, address, socket.getLocalPort());
		System.out.println(registration);
		registryConnection.sendData(registration.getByte());
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void openRoutingTableConnections() throws IOException{
		for(int i = 0; i < routingTable.getSize(); i++) {
			InetAddress addr = InetAddress.getByAddress(routingTable.get(i).getIP_address());
			Socket socket = new Socket(addr,routingTable.get(i).getPortNumber());
			TCPConnection tcpConnection = new TCPConnection(socket);
			Thread connection = new Thread(tcpConnection);
			connection.start();
		}
	}
	
	@Override
	public void interactiveCommandEvent(String command) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		System.out.println("Starting Message Node");
		String registryHost = args[0];
		int registryPortNumber = Integer.parseInt(args[1]);
		MessagingNode mNode = new MessagingNode(registryHost, registryPortNumber);
		InteractiveCommandParser interactiveCommandParser = new InteractiveCommandParser(mNode);
		Thread commandParser = new Thread(interactiveCommandParser);
		commandParser.start();
		while(commandParser.isAlive()) {
			
		}
	}

}
