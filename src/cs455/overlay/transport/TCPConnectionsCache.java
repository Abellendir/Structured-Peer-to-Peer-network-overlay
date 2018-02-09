/**
 * 
 */
package cs455.overlay.transport;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Adam Bellendir
 *
 */
public class TCPConnectionsCache {
	
	private Thread serverThread = null;
	private List<Thread> tcpConnectionsCache;
	private TCPConnection registry;
	private TCPConnection[] tcpSenders;
	private static final TCPConnectionsCache cache = new TCPConnectionsCache();
	
	/**
	 * 
	 */
	private TCPConnectionsCache() {
		tcpConnectionsCache = new ArrayList<Thread>();
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
	public Thread getConnection(int index) {
		return tcpConnectionsCache.get(index);
	}
	
	/**
	 * 
	 * @param connection
	 */
	public void addConnection(Thread connection) {
		tcpConnectionsCache.add(connection);
	}
	
	/**
	 * 
	 * @param serverThread
	 */
	public void addServerConnection(Thread serverThread) {
		this.serverThread = serverThread;
	}
	
	/**
	 * 
	 * @return
	 */
	public Thread getServerThread() {
		return serverThread;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSize() {
		return tcpConnectionsCache.size();
	}
	
	/**
	 * 
	 * @param size
	 */
	public void setSizeOfSenders(int size) {
		tcpSenders = new TCPConnection[size];
	}
	
	/**
	 * 
	 * @param index
	 * @param sender
	 */
	public void addSender(int index, TCPConnection sender) {
		tcpSenders[index] = sender;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public TCPConnection getSender(int index) {
		return tcpSenders[index];
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
}
