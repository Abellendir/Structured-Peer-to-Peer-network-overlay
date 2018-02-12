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
import cs455.overlay.util.StatisticsCollectorAndDisplay;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
import cs455.overlay.wireformats.OverlayNodeSendsDeregistration;
import cs455.overlay.wireformats.OverlayNodeSendsRegistration;
import cs455.overlay.wireformats.RegistryReportsRegistrationStatus;
import cs455.overlay.wireformats.RegistryRequestTaskInitiate;
import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
import cs455.overlay.wireformats.RegistrySendsNodeManifest;

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
	private StatisticsCollectorAndDisplay statisticsCollector = new StatisticsCollectorAndDisplay();
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
		int nodeID = event.getStatus();
		int sent = event.getTotalSent();
		int received = event.getTotalNumPacketsRec();
		int relayed = event.getTotalRelayed();
		long sumDataSent = event.getSumSentData();
		long sumDataReceived = event.getSumPacketsRec();
		statisticsCollector.addData(nodeID,sent,received,relayed,sumDataSent,sumDataReceived);
	}

	private void overlayNodeReportsDone(OverlayNodeReportsTaskFinished event) {
		int nodeID = event.getStatus();
		RegistryRequestsTrafficSummary send = new RegistryRequestsTrafficSummary();
		TCPConnection conn = registry.getConnectionOffID(nodeID);
		try {
			conn.sendData(send.getByte());
		} catch (IOException e) {
			System.out.println("Failled to send request for traffic summary");
			e.printStackTrace();
		}
	}

	private void registryAcknowledgesOverlaySetup(NodeReportsOverlaySetupStatus event) {
		
	}

	private void registryDeregistersNode(OverlayNodeSendsDeregistration request) {
		int node = request.getStatus();
		
		
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
		RegistryReportsRegistrationStatus send = null;
		int ID = randomizedIDs.remove(0);
		TCPConnection conn = tcpConnectionsCache.getConnection(request.getIP_address(),request.getPortNumber());
		RoutingEntry entry = new RoutingEntry(ID,ID,request.getIP_address(),request.getPortNumber(),conn);
		if(registry.contains(entry)) {
			send = new RegistryReportsRegistrationStatus(-1,"Already registered with registry");
			randomizedIDs.add(ID);
		}else if(request.getStatus() == -1) {
			send = new RegistryReportsRegistrationStatus(-1,"Sent IP Address is different than actual IP address.");
			randomizedIDs.add(ID);
		}else{
			registry.add(entry);
			send = new RegistryReportsRegistrationStatus(ID,"Registration request successful. Number of registered nodes is (" + registry.getSize() +").");
		}
		if(debug)System.out.print(send);
		try {
			conn.sendData(send.getByte());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private void listMessagingNodes() {
		System.out.println(registry);
	}
	
	/**
	 * 
	 */
	private void setupOverLay(int numberEntries){
		registry.sort();
		int[] allNodes = registry.allNodes();
		for(int index = 0; index < registry.getSize(); index++) {
			RoutingEntry entry = registry.get(index);
			entry.setTable(createTableForEntry(entry,numberEntries , index));
			RoutingTable temp = entry.getTable();
			sendManifest(numberEntries, allNodes, entry, temp);
		}
		//ADD the calls to each node with the manifest response;
	}

	private void sendManifest(int numberEntries, int[] allNodes, RoutingEntry entry, RoutingTable temp) {
		RegistrySendsNodeManifest send;
		int[] node = new int[numberEntries];
		byte[][] IP_addresses = new byte[numberEntries][];
		int[] portNumber = new int[numberEntries];
		for(int i = 0; i < temp.getSize(); i++) {
			RoutingEntry e = temp.get(i);
			node[i] = e.getID();
			IP_addresses[i] = e.getIP_address();
			portNumber[i] = e.getPortNumber();
			send = new RegistrySendsNodeManifest(node,IP_addresses,portNumber,allNodes);
			try {
				e.getConnection().sendData(send.getByte());
			} catch (IOException e1) {
				System.out.println("Couldn't setup overlay for node "+ entry.getID());
				e1.printStackTrace();
			}
		}
	}
	
	private RoutingTable createTableForEntry(RoutingEntry routingEntry, int numberEntries, int index) {
		RoutingTable entryRoutingTable = new RoutingTable();
		int size = registry.getSize();
		for(int i = 0; i < numberEntries; i++) {
				int offset = (int) Math.pow(2, i);
				int nextIndex = offset + index;
				if(nextIndex%size == index) break; //verify
				
				entryRoutingTable.add((nextIndex >= size)? registry.get(nextIndex%size) : registry.get(nextIndex));
		}
		return entryRoutingTable;
	}
	/**
	 * 
	 */
	private void listRoutingTables() {
		for(int i = 0; i < registry.getSize(); i++) {
			System.out.println("Routing table for " + registry.get(i));
			System.out.println(registry.get(i).getTable() + "\n\n\n");
		}
	}
	
	/**
	 * 
	 * @param numberOfMessages
	 */
	private void start(int numberOfMessages) throws IOException{
		for(int i = 0; i < registry.getSize(); i++) {
			RegistryRequestTaskInitiate request = new RegistryRequestTaskInitiate(numberOfMessages);
			registry.get(i).getConnection().sendData(request.getByte());
		}
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
		switch(command) {
		case "list-messaging-nodes": listMessagingNodes();break;
		case "list-routing-tables": listRoutingTables();break;
		}
	}
	
	@Override
	public void interactiveCommandEvent(String[] command) {
		System.out.println(Arrays.toString(command));
		System.out.println(command[0]);
		System.out.println(command[1]);
		String cmd = command[0];
		int number = Integer.parseInt(command[1]);
		switch(cmd) {
			case "setup-overlay": setupOverLay(number);
								break;
			case "start":
							try {
								start(number);
							} catch (IOException e ) {
								e.printStackTrace();
								System.out.println("Failed to initiate task!");
							}
							break;
			default: System.out.println("Invalid command; valid commands are "
									+ "\"start\" \"int\" or \"setup-overlay\" \"int\"."); break;
		}
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
	}
}
