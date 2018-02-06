package cs455.overlay.transport;

import java.util.ArrayList;

public class TCPConnectionsCache {
	
	private ArrayList<TCPConnection> tcpConnectionCache;

	public TCPConnectionsCache() {
		// TODO Auto-generated constructor stub
	}
	
	public TCPConnection getConnection(int index) {
		return tcpConnectionCache.get(index);
	}
	
	public void addConnection(TCPConnection connection) {
		tcpConnectionCache.add(connection);
	}

}
