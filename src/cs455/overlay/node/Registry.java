package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
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
import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import cs455.overlay.wireformats.RegistryReportsRegistrationStatus;
import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
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
	private final TCPConnectionsCache tcpConnectionsCache = TCPConnectionsCache.getInstance();
	private final StatisticsCollectorAndDisplay statisticsCollector = new StatisticsCollectorAndDisplay();
	private final ArrayList<Integer> randomizedIDs = new ArrayList<Integer>();
	private int taskCompleteTracker;
	private final RoutingTable registry = new RoutingTable();
	
	private synchronized int getTaskCompleteTracker() {
		return taskCompleteTracker;
	}

	private synchronized void incrementTaskCompleteTracker() {
		this.taskCompleteTracker++;
	}
	
	private synchronized void resetCounter() {
		this.taskCompleteTracker = 0;
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public Registry() {
		try {
			System.out.println(Arrays.toString(InetAddress.getLocalHost().getAddress()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		intitializeRandomArrayListOfIDs();
	}
	
	/**
	 * 
	 */
	private void intitializeRandomArrayListOfIDs() {
		for(int randID = 0; randID < 128; randID++) {
			randomizedIDs.add(randID);
		}
		Collections.shuffle(randomizedIDs);
	}

	@Override
	public void onEvent(Event event) {
		switch(event.getType()) {
		case 2: registerNewNode((OverlayNodeSendsRegistration) event); break;
		case 4: try {
				registryDeregistersNode((OverlayNodeSendsDeregistration) event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} break;
		case 7: registryAcknowledgesOverlaySetup((NodeReportsOverlaySetupStatus) event); break;
		case 10: overlayNodeReportsDone((OverlayNodeReportsTaskFinished) event); break;
		case 12: overlayNodeReportsSummary((OverlayNodeReportsTrafficSummary) event); break;
		default: break;
		}
	}

	/**
	 * 
	 * @param event
	 */
	private void overlayNodeReportsSummary(OverlayNodeReportsTrafficSummary event) {
		//System.out.println(event);
		int nodeID = event.getStatus();
		int sent = event.getTotalSent();
		int received = event.getTotalNumPacketsRec();
		int relayed = event.getTotalRelayed();
		long sumDataSent = event.getSumSentData();
		long sumDataReceived = event.getSumPacketsRec();
		statisticsCollector.addData(nodeID,sent,received,relayed,sumDataSent,sumDataReceived);
		incrementTaskCompleteTracker();
		if(getTaskCompleteTracker() == registry.getSize()) {
			System.out.println(statisticsCollector);
			resetCounter();
			statisticsCollector.clear();
		}
		
	}

	private synchronized void overlayNodeReportsDone(OverlayNodeReportsTaskFinished event) {
		//System.out.println(event);
		incrementTaskCompleteTracker();
		System.out.println(registry.getSize());
		if(registry.getSize() == this.getTaskCompleteTracker()) {
			try {
				Thread.sleep(15000);
				registryRequestTrafficSummary();
				resetCounter();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void registryRequestTrafficSummary() {
		for(int i = 0; i < registry.getSize(); i++) {
			RegistryRequestsTrafficSummary send = new RegistryRequestsTrafficSummary();
			TCPConnection conn = registry.get(i).getConnection();
			try {
				conn.sendData(send.getByte());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void registryAcknowledgesOverlaySetup(NodeReportsOverlaySetupStatus event) {
		System.out.println(event);
	}

	private void registryDeregistersNode(OverlayNodeSendsDeregistration request) throws IOException{
		System.out.println(request);
		int nodeID = request.getStatus();
		RegistryReportsDeregistrationStatus send = new RegistryReportsDeregistrationStatus(nodeID,"Deregistartion was successful of node " + nodeID);
		if(request.getStatus() == -1) {
			send.setMessage("Deregistration unsuccessful due to mismatch of IP actual and expected");
			System.out.println(send);
			registry.getConnection(nodeID).sendData(send.getByte());
			return;
		}
		if(!registry.contains(nodeID)) {
			send.setMessage("Deregistration unsuccessful; node is not contained in registry.");
			System.out.println(send);
			TCPConnection conn = tcpConnectionsCache.getConnection(request.getIP_address(),request.getPortNumber());
			conn.sendData(send.getByte());
			return;
		}
		TCPConnection conn = registry.getConnection(nodeID);
		registry.remove(nodeID);
		randomizedIDs.add(nodeID);
		System.out.println(send);
		conn.sendData(send.getByte());
		System.out.println("Removed node "+ nodeID);
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
		System.out.println("Registering Node");
		System.out.println(request);
		RegistryReportsRegistrationStatus send = null;
		int ID = randomizedIDs.remove(0);
		TCPConnection conn = tcpConnectionsCache.getConnection(request.getSocketAddress(),request.getSocketPort());
		RoutingEntry entry = new RoutingEntry(ID,request.getIP_address(),request.getPortNumber(),conn);
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
		System.out.print(send);
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
		System.out.println(Arrays.toString(allNodes));
		for(int index = 0; index < registry.getSize(); index++) {
			RoutingEntry entry = registry.get(index);
			System.out.println("Table for: " + entry);
			entry.setTable(createTableForEntry(entry,numberEntries , index));
			System.out.println(entry.getTable());
			RoutingTable temp = entry.getTable();
				try {
					sendManifest(numberEntries, allNodes, entry, temp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		//ADD the calls to each node with the manifest response;
	}

	private void sendManifest(int numberEntries, int[] allNodes, RoutingEntry entry, RoutingTable temp) throws IOException{
		RegistrySendsNodeManifest send = new RegistrySendsNodeManifest(entry,allNodes,numberEntries);
		System.out.print(send);
		entry.getConnection().sendData(send.getByte());
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
			RegistryRequestsTaskInitiate request = new RegistryRequestsTaskInitiate(numberOfMessages);
			registry.get(i).getConnection().sendData(request.getByte());
		}
	}

	/**
	 *Provides the link from interactiveCommandParser to initiate the requested task on the node
	 *@param command 
	 */
	@Override
	public void interactiveCommandEvent(String command) {
		switch(command) {
		case "list-messaging-nodes": listMessagingNodes();break;
		case "list-routing-tables": listRoutingTables();break;
		default: System.out.println("Invalid command; valid commands are "
				+ "\n\"start\" \"int\""
				+ "\n\"setup-overlay\" \"int\""
				+ "\n\"list-messaging-nodes\""
				+ "\n\"list-routing-tables\"."); break;
		}
	}
	
	@Override
	public void interactiveCommandEvent(String[] command) {
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
									+ "\n\"start\" \"int\""
									+ "\n\"setup-overlay\" \"int\""
									+ "\n\"list-messaging-nodes\""
									+ "\n\"list-routing-tables\"."); break;
		}
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		System.out.println("Starting Registry");
		EventFactory eventFactory = EventFactory.getInstance();
		TCPConnectionsCache cache = TCPConnectionsCache.getInstance();
		try {
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
			TCPServerThread serverThread = new TCPServerThread(serverSocket);
			new Thread(serverThread).start();
			cache.addServerConnection(serverThread);
			Registry registry = new Registry();
			eventFactory.giveNode(registry);
			new Thread(eventFactory).start();
			InteractiveCommandParser commandParser = new InteractiveCommandParser(registry);
			new Thread(commandParser).start();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
