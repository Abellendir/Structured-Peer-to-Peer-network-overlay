package cs455.overlay.node;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import cs455.overlay.routing.RoutingEntry;
import cs455.overlay.routing.RoutingTable;
import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPConnectionsCache;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.util.InteractiveCommandParser;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
import cs455.overlay.wireformats.OverlayNodeSendsDeregistration;
import cs455.overlay.wireformats.OverlayNodeSendsRegistration;
import cs455.overlay.wireformats.RegistryReportsRegistrationStatus;

/**
 *
 * Registry Node
 *
 * @author Adam Bellendir
 *
 */
public class Registry implements Node {
	
	private boolean debug = true;
	private Thread serverThread = null;
	private EventFactory eventFactory = EventFactory.getInstance();
	private TCPConnectionsCache tcpConnectionsCache = TCPConnectionsCache.getInstance();
	private ArrayList<Integer> randomizedIDs = new ArrayList<Integer>();
	private ServerSocket serverSocket;
	private RoutingTable registry = new RoutingTable();
	
	/**
	 * @throws IOException 
	 * 
	 */
	public Registry(int registryPortNumber) {
		try {
			startServerThread(registryPortNumber);
			eventFactory.giveNode(this);
			intitializeRandomArrayListOfIDs();
			
		} catch (IOException e) {
			System.out.print("DEAD");
			e.printStackTrace();
		}
		eventFactory.giveNode(this);
	}
	
	private void intitializeRandomArrayListOfIDs() {
		for(int randID = 0; randID < 128; randID++) {
			randomizedIDs.add(randID);
		}
		Collections.shuffle(randomizedIDs);
	}

	@Override
	public void onEvent(Event event) {
		System.out.println(event.getType());
		switch(event.getType()) {
		case 2: registerNewNode((OverlayNodeSendsRegistration) event); break;
		case 4: registryDeregistersNode((OverlayNodeSendsDeregistration) event); break;
		case 7: registryAcknowledgesOverlaySetup((NodeReportsOverlaySetupStatus) event); break;
		case 10: overlayNodeReportsDone((OverlayNodeReportsTaskFinished) event); break;
		case 12: overlayNodeReportsSummary((OverlayNodeReportsTrafficSummary) event); break;
		default: break;
		}
	}

	private void overlayNodeReportsSummary(OverlayNodeReportsTrafficSummary event) {
		// TODO Auto-generated method stub
		
	}

	private void overlayNodeReportsDone(OverlayNodeReportsTaskFinished event) {
		// TODO Auto-generated method stub
		
	}

	private void registryAcknowledgesOverlaySetup(NodeReportsOverlaySetupStatus event) {
		// TODO Auto-generated method stub
		
	}

	private void registryDeregistersNode(OverlayNodeSendsDeregistration request) {
		
	}
	
	/**
	 * Handles New registration of a node
	 * checks 
	 * 		- If the TCPConnection that initiated is null or dead, in which case it drops the message and moves on.
	 * 		- if nodes is already registered, send message saying so.
	 * 		- if Nodes presented IP address doesn't match sockets end IP address, send message saying so.
	 * @param request
	 */
	public void registerNewNode(OverlayNodeSendsRegistration request) {
		System.out.println("Register Node");
		System.out.println(request.toString());
		RegistryReportsRegistrationStatus send = null;
		int ID = randomizedIDs.remove(0);
		TCPConnection conn = tcpConnectionsCache.getConnection(request.getIP_address(),request.getPortNumber());
		if(conn==null) {
			System.out.println("NULL connection");
			return;
		}
		RoutingEntry entry = new RoutingEntry(ID,ID,request.getIP_address(),request.getPortNumber(),conn);
		if(registry.contains(entry)) {
			System.out.println("ALready registered");
			send = new RegistryReportsRegistrationStatus(-1,"Already registered with registry");
			randomizedIDs.add(ID);
		}else if(!Arrays.equals(request.getIP_address(), conn.getAddress())) {
			System.out.println("Doesn't match IP");
			send = new RegistryReportsRegistrationStatus(-1,"Sent IP Address is different than actual IP address.");
			randomizedIDs.add(ID);
		}else{
			registry.add(entry);
			send = new RegistryReportsRegistrationStatus(ID,"Registration request successful. Number of registered nodes is (" + registry.getSize() +").");
		}
		if(debug)System.out.print(send);
		try {
			byte[] bytes = send.getByte();
			conn.sendData(bytes);
		} catch (IOException e) {
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
     * @return 
     * @throws IOException
     */
	public void startServerThread(int portNumber) throws IOException {
		ServerSocket serverSocket = new ServerSocket(portNumber);
		TCPServerThread tcpServerThread = new TCPServerThread(serverSocket);
		serverThread = new Thread(tcpServerThread);
		serverThread.start();
		tcpConnectionsCache.addServerConnection(serverThread);
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
	
	@Override
	public void interactiveCommandEvent(String command) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		System.out.println("Starting Registry");
		Registry registry = new Registry(Integer.parseInt(args[0]));
		InteractiveCommandParser commandParser = new InteractiveCommandParser(registry);
		Thread interactiveCommandParser = new Thread(commandParser);
		interactiveCommandParser.start();
		while(interactiveCommandParser.isAlive()) {
			
		}
	}

}
