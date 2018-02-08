package cs455.overlay.transport;

import java.util.ArrayList;
import java.util.List;

public class TCPConnectionsCache {
	
	private Thread serverThread = null;
	
	private List<Thread> tcpConnectionsCache;
	
	private static TCPConnectionsCache cache;

	private TCPConnectionsCache() {
		tcpConnectionsCache = new ArrayList<Thread>();
	}
	
	public static TCPConnectionsCache getInstance() {
		if(cache != null) {
			cache = new TCPConnectionsCache();
		}
		return cache;
	}
	
	public Thread getConnection(int index) {
		return tcpConnectionsCache.get(index);
	}
	
	public void addConnection(Thread connection) {
		tcpConnectionsCache.add(connection);
	}
	
	public void addServerConnection(Thread serverThread) {
		this.serverThread = serverThread;
	}
	
	public Thread getServerThread() {
		return serverThread;
	}
}
