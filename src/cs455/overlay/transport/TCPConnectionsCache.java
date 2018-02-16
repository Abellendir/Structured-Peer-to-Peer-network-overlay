/**
 * 
 */
package cs455.overlay.transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Adam Bellendir
 *
 */
public class TCPConnectionsCache {
	
	private TCPServerThread serverThread = null;
	private static List<TCPConnection> tcpConnectionsCache = new ArrayList<TCPConnection>();
	private TCPConnection registry;
	private static final TCPConnectionsCache cache = new TCPConnectionsCache();
	
	/**
	 * 
	 */
	private TCPConnectionsCache() {
	}
	
	/**
	 * 
	 * @return
	 */
	public static TCPConnectionsCache getInstance() {
		return cache;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public synchronized TCPConnection getConnection(byte[] addr, int portNumber) {
		for(TCPConnection conn: tcpConnectionsCache) {
			if(Arrays.equals(addr, conn.getAddress()) && portNumber == conn.getPortNumber()) {
				return conn;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param connection
	 */
	public synchronized void addConnection(TCPConnection connection) {
		tcpConnectionsCache.add(connection);
	}
	
	/**
	 * 
	 * @param serverThread
	 */
	public void addServerConnection(TCPServerThread serverThread) {
		this.serverThread = serverThread;
	}
	
	/**
	 * 
	 * @return
	 */
	public TCPServerThread getServerThread() {
		return serverThread;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized int getSize() {
		return tcpConnectionsCache.size();
	}
	
	/**
	 * 
	 * @param registry
	 */
	public void addRegistry(TCPConnection registry) {
		this.registry = registry;
	}
	
	/**
	 * 
	 * @return
	 */
	public TCPConnection getRegistry() {
		return registry;
	}

	/**
	 * @param tcpConnection
	 */
	public synchronized void remove(TCPConnection tcpConnection) {
		tcpConnectionsCache.remove(tcpConnection);
	}
}
