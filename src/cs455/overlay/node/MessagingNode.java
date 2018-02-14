/**
 * 
 */
package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import cs455.overlay.routing.RoutingEntry;
import cs455.overlay.routing.RoutingTable;
import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPConnectionsCache;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.util.InteractiveCommandParser;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.OverlayNodeSendsRegistration;
import cs455.overlay.wireformats.RegistryReportsRegistrationStatus;
import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
import cs455.overlay.wireformats.RegistrySendsNodeManifest;

/**
 * @author Adam Bellendir
 *
 */
public class MessagingNode implements Node {
	
	private int nodeID;
	private final RoutingTable routingTable = new RoutingTable();
	private TCPConnectionsCache cache = TCPConnectionsCache.getInstance();
	private int[] allNodes;
	private byte[] IP_address;
	private ServerSocket serverSocket;
	private int portNumber;
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
			serverSocket = new ServerSocket(0);
			this.portNumber = serverSocket.getLocalPort();
			System.out.println("Initialized address in bytes:" + Arrays.toString(IP_address) + "\nAddress as InetAddress: " + InetAddress.getLocalHost());
			System.out.println("On port: " + portNumber);
		} catch (UnknownHostException e) {
			System.out.println("\nUnknownHostException in constructor");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEvent(Event event) {
		int type = event.getType();
		switch(type) {
		case 3: registryReportsRegistrationStatus((RegistryReportsRegistrationStatus) event);
				break;
		case 5: registryReportsDeregistrationStatus((RegistryReportsRegistrationStatus) event);
				break;
		case 6: registrySendsNodeManifest((RegistrySendsNodeManifest) event);
				break;
		case 8: registryRequestsTaskInitiate((RegistryRequestsTaskInitiate) event);
				break;
		case 11: registryRequestsTrafficSummary((RegistryRequestsTrafficSummary) event);
				 break;
		default: break;
		}
		
	}
	
	private void registryRequestsTrafficSummary(RegistryRequestsTrafficSummary event) {
		// TODO Auto-generated method stub
		
	}

	private void registryRequestsTaskInitiate(RegistryRequestsTaskInitiate event) {
		// TODO Auto-generated method stub
		
	}

	private void registrySendsNodeManifest(RegistrySendsNodeManifest event){
		System.out.print(event);
		
		int[] nodeID = event.getNodeID();
		byte[][] IP = event.getIP_addresses();
		int[] ports = event.getPortNumbers();
		for(int i = 0; i < nodeID.length;i++) {
			try {
				InetAddress addr = InetAddress.getByAddress(IP[i]);
				Socket socket = new Socket(addr,ports[i]);
				TCPConnection conn = new TCPConnection(socket);
				Thread connection = new Thread(conn);
				connection.start();
				RoutingEntry entry = new RoutingEntry(nodeID[i],IP[i],ports[i],conn);
				routingTable.add(entry);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void registryReportsDeregistrationStatus(RegistryReportsRegistrationStatus event) {
		// TODO Auto-generated method stub
		
	}

	private void registryReportsRegistrationStatus(RegistryReportsRegistrationStatus event) {
		this.nodeID = event.getStatus();
		System.out.println(event);
	}

	public void start() {
		
	}
	/**
	 * 
	 */
	private synchronized void printCountersandDiagnostics() {
		String countersandDiagnostics ="";
		countersandDiagnostics += "Number of messages:"+
								  "\n\t-Sent:         " + sendTracker +
								  "\n\t-Received:     " + receiveTracker +
								  "\n\t-Relayed:      " + relayedTracker +
								  "\n\t-Sum Sent:     " + sendSummation +
								  "\n\t-Sum Received: " + receiveSummation +
								  "\n";
		System.out.println(countersandDiagnostics);
	}
	
	/**
	 * 
	 */
	private void exitOverlay() {
		
	}
	
	/**
	 * 
	 * @param registryPortNumber 
	 * @param hostName 
	 * @return
	 * @throws IOException
	 */
	private void openServerSocket(String hostName, int registryPortNumber) throws IOException{
		TCPServerThread serverThread = new TCPServerThread(this.serverSocket);
		Thread server = new Thread(serverThread);
		server.start();
		cache.addServerConnection(serverThread);
		registerWithRegistry(hostName, registryPortNumber);
	}
	

	private void registerWithRegistry(String hostName, int portNumber) throws IOException{
		Socket socket = new Socket(hostName,portNumber);
		TCPConnection registryConnection = new TCPConnection(socket);
		Thread registry = new Thread(registryConnection);
		registry.start();
		cache.addRegistry(registryConnection);
		OverlayNodeSendsRegistration registration = 
				new OverlayNodeSendsRegistration(IP_address.length, this.IP_address, this.portNumber);
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
		if(command.equals("print-counters-and-diagnostics")) {
			printCountersandDiagnostics();
		}
		else if(command.equals("exit-overlay")) {
			exitOverlay();
		}else {
			System.out.println("invalid command: valid commands are \"print-counters-and-diagnostics\" or \"exit-overlay\"");
		}
		
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		System.out.println("Starting Message Node");
		String registryHost = args[0];
		EventFactory eventFactory = EventFactory.getInstance();
		int registryPortNumber = Integer.parseInt(args[1]);
		MessagingNode mNode = new MessagingNode(registryHost, registryPortNumber);
		try {
			mNode.openServerSocket(args[0],Integer.parseInt(args[1]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eventFactory.giveNode(mNode);
		InteractiveCommandParser interactiveCommandParser = new InteractiveCommandParser(mNode);
		Thread commandParser = new Thread(interactiveCommandParser);
		commandParser.start();
	}

	@Override
	public void interactiveCommandEvent(String[] command) {
		System.out.println(
				  "Not a valid command for Messaging Node:"
				+ "\nValid commands are:"
				+ "\n\t\"exit-overlay\""
				+ "\n\t\"print-counters-and-diagnostics\"");
	}

}
