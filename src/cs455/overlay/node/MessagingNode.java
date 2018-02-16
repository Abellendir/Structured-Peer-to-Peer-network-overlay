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
import java.util.Random;

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
import cs455.overlay.wireformats.OverlayNodeSendsData;
import cs455.overlay.wireformats.OverlayNodeSendsDeregistration;
import cs455.overlay.wireformats.OverlayNodeSendsRegistration;
import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
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
	private int[] nodesInRoutingTable;
	private TCPConnectionsCache cache = TCPConnectionsCache.getInstance();
	private int[] allNodes;
	private final byte[] IP;
	private int portNumber;
	private int sendTracker = 0;
	private int receiveTracker = 0;
	private int relayedTracker = 0;
	private long sendSummation = 0;
	private long receiveSummation = 0;

	/**
	 * 
	 */
	public MessagingNode(byte[] IP,int port) {
		this.IP = IP;
		this.portNumber = port;
		System.out.println("Initialized address in bytes:" + Arrays.toString(IP));
		System.out.println("On port: " + portNumber);

	}

	/* (non-Javadoc)
	 * @see cs455.overlay.node.Node#onEvent(cs455.overlay.wireformats.Event)
	 */
	@Override
	public void onEvent(Event event) {
		int type = event.getType();
		switch (type) {
		case 3:
			registryReportsRegistrationStatus((RegistryReportsRegistrationStatus) event);
			break;
		case 5:
			registryReportsDeregistrationStatus((RegistryReportsDeregistrationStatus) event);
			break;
		case 6:
			registrySendsNodeManifest((RegistrySendsNodeManifest) event);
			break;
		case 8:
			registryRequestsTaskInitiate((RegistryRequestsTaskInitiate) event);
			break;
		case 9:
			overlayNodeSendsData((OverlayNodeSendsData) event);
			break;
		case 11:
			registryRequestsTrafficSummary((RegistryRequestsTrafficSummary) event);
			break;
		default:
			break;
		}

	}

	/**
	 * @param event
	 */
	private void overlayNodeSendsData(OverlayNodeSendsData event) {
		if (event.getDestinationId() == this.nodeID) {
			overlayNodeReceivesPayload(event);
		} else {
			int destination = event.getDestinationId();
			int payload = event.getPayload();
			int source = event.getSourceId();
			int[] dataTrace = event.getDisseminationNodeIDtrace();
			int[] trace = null;
			if (dataTrace == null) {
				trace = new int[1];
				trace[0] = this.nodeID;
			} else {
				trace = new int[dataTrace.length + 1];
				for (int i = 0; i < trace.length - 1; i++) {
					trace[i] = dataTrace[i];
				}
				trace[trace.length - 1] = this.nodeID;
			}
			int index = chooseRoutingTableEntry(destination);
			try {
				relayPayload(trace, destination, source, payload, index);
				incrementRelayed();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	private synchronized void incrementRelayed() {
		relayedTracker++;
	}

	/**
	 * @param event
	 */
	private void overlayNodeReceivesPayload(OverlayNodeSendsData event) {
		//System.out.println(event);
		incrementReceivedTracker();
		incrementReceivedSum(event.getPayload());
	}

	/**
	 * @param payload
	 */
	private synchronized void incrementReceivedSum(int payload) {
		receiveSummation += payload;
	}

	/**
	 * 
	 */
	private synchronized void incrementReceivedTracker() {
		receiveTracker++;
	}

	/**
	 * @param event
	 */
	private synchronized void registryRequestsTrafficSummary(RegistryRequestsTrafficSummary event) {
		OverlayNodeReportsTrafficSummary send = new OverlayNodeReportsTrafficSummary(this.nodeID, this.sendTracker,
				this.relayedTracker, this.sendSummation, this.receiveTracker, this.receiveSummation);
		try {
			cache.getRegistry().sendData(send.getByte());
			resetCounts();
		} catch (IOException e) {
			System.out.println("Failed to send summary");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void resetCounts() {
		sendTracker = 0;
		receiveTracker = 0;
		relayedTracker = 0;
		sendSummation = 0;
		receiveSummation = 0;
	}

	/**
	 * @param event
	 */
	private void registryRequestsTaskInitiate(RegistryRequestsTaskInitiate event) {
		//System.out.println(event);
		int rounds = event.getStatus();
		Random rand = new Random();
		int[] trace = null;
		for (int i = 1; i <= rounds; i++) {
			int destination;
			do {
				destination = allNodes[rand.nextInt(this.allNodes.length)];
			} while (destination == this.nodeID);
			int payload = rand.nextInt();
			int index = chooseRoutingTableEntry(destination);
			try {
				relayPayload(trace, destination, this.nodeID, payload, index);
				incrementSendSum(payload);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sendTracker++;
		}
		overlayNodeReportsTaskFinished();
	}

	/**
	 * 
	 */
	private void overlayNodeReportsTaskFinished() {
		TCPConnection conn = cache.getRegistry();
		OverlayNodeReportsTaskFinished send = new OverlayNodeReportsTaskFinished(IP.length, IP,
				portNumber, nodeID);
		try {
			conn.sendData(send.getByte());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param trace
	 * @param destination
	 * @param payload
	 * @param index
	 */
	private void relayPayload(int[] trace, int destination, int source, int payload, int index) throws IOException {
		TCPConnection conn = routingTable.get(index).getConnection();
		OverlayNodeSendsData data = new OverlayNodeSendsData(destination, source, payload, trace);
		conn.sendData(data.getByte());
	}

	/**
	 * @param payload
	 */
	private synchronized void incrementSendSum(int payload) {
		sendSummation += payload;
	}

	/**
	 * @param destination
	 * @return
	 */
	private int chooseRoutingTableEntry(int destination) {
		int length = nodesInRoutingTable.length;
		if (nodesInRoutingTable[0] == destination)
			return 0;
		if (nodesInRoutingTable[1] == destination)
			return 1;
		if (nodesInRoutingTable[length - 1] == destination)
			return length - 1;
		int index = 1;
		for (int i = 2; i < length - 1; i++) {
			if (nodesInRoutingTable[i] == destination)
				return i;
			if (nodesInRoutingTable[i - 1] < destination && destination < nodesInRoutingTable[i])
				return (i - 1);
			if (nodesInRoutingTable[i - 1] > destination && destination > nodesInRoutingTable[i])
				return (i - 1);
			if (nodesInRoutingTable[i] < destination && destination < nodesInRoutingTable[i + 1])
				return (i);
			if (nodesInRoutingTable[i] > destination && destination > nodesInRoutingTable[i + 1])
				return (i);
		}
		return index;
	}

	/**
	 * @param event
	 */
	private void registrySendsNodeManifest(RegistrySendsNodeManifest event) {
		//System.out.print(event);
		int status = this.nodeID;
		nodesInRoutingTable = event.getNodeID();
		byte[][] IP = event.getIP_addresses();
		int[] ports = event.getPortNumbers();
		allNodes = event.getAllNodes();
		for (int i = 0; i < nodesInRoutingTable.length; i++) {
			try {
				InetAddress addr = InetAddress.getByAddress(IP[i]);
				Socket socket = new Socket(addr, ports[i]);
				TCPConnection conn = new TCPConnection(socket);
				Thread connection = new Thread(conn);
				connection.start();
				RoutingEntry entry = new RoutingEntry(nodesInRoutingTable[i], IP[i], ports[i], conn);
				routingTable.add(entry);
			} catch (UnknownHostException e) {
				status = -1;
				e.printStackTrace();
			} catch (IOException e) {
				status = -1;
				e.printStackTrace();
			}
		}
		NodeReportsOverlaySetupStatus send;
		if (status == -1) {
			send = new NodeReportsOverlaySetupStatus(status, "Node unsuccessful in setting up overlay");
		} else {
			send = new NodeReportsOverlaySetupStatus(status, "Successfully Setup overlay");
		}
		try {
			cache.getRegistry().sendData(send.getByte());
		} catch (IOException e) {
			System.out.println("Was unable to send response");
			e.printStackTrace();
		}
	}

	/**
	 * @param event
	 */
	private void registryReportsDeregistrationStatus(RegistryReportsDeregistrationStatus event) {
		System.out.println(event);

	}

	/**
	 * @param event
	 */
	private void registryReportsRegistrationStatus(RegistryReportsRegistrationStatus event) {
		this.nodeID = event.getStatus();
		System.out.println(event);
	}

	/**
	 * 
	 */
	private synchronized void printCountersandDiagnostics() {
		String countersandDiagnostics = "";
		countersandDiagnostics += "Number of messages:" + "\n\t-Sent:         " + sendTracker + "\n\t-Received:     "
				+ receiveTracker + "\n\t-Relayed:      " + relayedTracker + "\n\t-Sum Sent:     " + sendSummation
				+ "\n\t-Sum Received: " + receiveSummation + "\n";
		System.out.println(countersandDiagnostics);
	}

	/**
	 * 
	 */
	private void exitOverlay() {
		OverlayNodeSendsDeregistration send = new OverlayNodeSendsDeregistration(IP, portNumber, nodeID);
		try {
			cache.getRegistry().sendData(send.getByte());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param hostName
	 * @param portNumber
	 * @throws IOException
	 */
	private void registerWithRegistry(String hostName, int portNumber) throws IOException {
		Socket socket = new Socket(hostName, portNumber);
		TCPConnection registryConnection = new TCPConnection(socket);
		Thread registry = new Thread(registryConnection);
		registry.start();
		cache.addRegistry(registryConnection);
		OverlayNodeSendsRegistration registration = new OverlayNodeSendsRegistration(IP.length, IP,
				this.portNumber);
		//System.out.println(registration);
		registryConnection.sendData(registration.getByte());
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void openRoutingTableConnections() throws IOException {
		for (int i = 0; i < routingTable.getSize(); i++) {
			InetAddress addr = InetAddress.getByAddress(routingTable.get(i).getIP_address());
			Socket socket = new Socket(addr, routingTable.get(i).getPortNumber());
			TCPConnection tcpConnection = new TCPConnection(socket);
			Thread connection = new Thread(tcpConnection);
			connection.start();
		}
	}

	/**
	 * Provides the link from interactiveCommandParser to initiate the requested
	 * task on the node
	 * 
	 * @param command
	 */
	@Override
	public void interactiveCommandEvent(String command) {
		if (command.equals("print-counters-and-diagnostics")) {
			printCountersandDiagnostics();
		} else if (command.equals("exit-overlay")) {
			exitOverlay();
		} else {
			System.out.println(
					"invalid command: valid commands are \"print-counters-and-diagnostics\" or \"exit-overlay\"");
		}

	}

	/* (non-Javadoc)
	 * @see cs455.overlay.node.Node#interactiveCommandEvent(java.lang.String[])
	 */
	@Override
	public void interactiveCommandEvent(String[] command) {
		System.out.println("Not a valid command for Messaging Node:" + "\nValid commands are:" + "\n\t\"exit-overlay\""
				+ "\n\t\"print-counters-and-diagnostics\"");
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting Message Node");
		TCPConnectionsCache cache = TCPConnectionsCache.getInstance();
		EventFactory eventFactory = EventFactory.getInstance();
		byte[] IP = null;
		try {
			IP = InetAddress.getLocalHost().getAddress();
			ServerSocket serverSocket = new ServerSocket(0);
			TCPServerThread serverThread = new TCPServerThread(serverSocket);
			new Thread(serverThread).start();
			cache.addServerConnection(serverThread);
			MessagingNode mNode = new MessagingNode(IP,serverSocket.getLocalPort());
			mNode.registerWithRegistry(args[0], Integer.parseInt(args[1]));
			eventFactory.giveNode(mNode);
			new Thread(eventFactory).start();
			InteractiveCommandParser interactiveCommandParser = new InteractiveCommandParser(mNode);
			new Thread(interactiveCommandParser).start();
		} catch (UnknownHostException e1) {
			System.out.println("Unknown Host");
			e1.printStackTrace();
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("Unable to open socket");
			e1.printStackTrace();
			System.exit(0);         
		}
	}

}
