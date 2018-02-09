package cs455.overlay.node;

import java.io.IOException;
import java.net.ServerSocket;

import cs455.overlay.transport.TCPConnectionsCache;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.util.InteractiveCommandParser;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.RegistryReportsRegistrationStatus;

/**
 *
 * Registry Node
 *
 * @author Adam Bellendir
 *
 */
public class Registry implements Node {
	
	private Thread serverThread = null;
	private Thread interactiveCommandParser = null;
	private EventFactory eventFactory = EventFactory.getInstance();
	private TCPConnectionsCache tcpConnectionsCache = TCPConnectionsCache.getInstance();
	private int registryPortNumber;
	private ServerSocket serverSocket;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public Registry(int registryPortNumber) {
		this.registryPortNumber = registryPortNumber;
		try {
			startServerThread(registryPortNumber);
			eventFactory.giveNode(this);
			
		} catch (IOException e) {
			System.out.print("DEAD");
			e.printStackTrace();
		}
		eventFactory.giveNode(this);
	}
	
	/**
	 * 
	 * @param event
	 */
	@Override
	public void onEvent(Event event) {
		switch(event.getType()) {
		case 2: break;
		case 3: break;
		case 4: break;
		case 5: break;
		case 6: break;
		case 7: break;
		case 8: break;
		case 9: break;
		case 10: break;
		case 11: break;
		case 12: break;
		default: break;
		
		}
		if(event.getType() == 2) {
			RegistryReportsRegistrationStatus send = new RegistryReportsRegistrationStatus(1,"Success");
			System.out.println(send);
			try {
				byte[] bytes = send.getByte();
				tcpConnectionsCache.getSender(0).sendData(bytes);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
