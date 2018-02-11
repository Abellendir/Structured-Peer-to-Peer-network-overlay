package cs455.overlay.routing;

import java.util.Arrays;

import cs455.overlay.transport.TCPConnection;

public class RoutingEntry implements Comparable<RoutingEntry>{
	
	private int ID;
	private int index;
	private byte[] IP_address;
	private int portNumber;
	private TCPConnection conn;
	
	public RoutingEntry(int ID, int index, byte[] IP_address, int portNumber, TCPConnection tcpConnection) {
		
		this.ID = ID;
		this.index = index;
		this.IP_address = IP_address;
		this.portNumber = portNumber;
		this.conn = tcpConnection;
	}

	public int getID() {
		return ID;
	}

	public int getIndex() {
		return index;
	}

	public byte[] getIP_address() {
		return IP_address;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public TCPConnection getConnection() {
		return conn;
	}
	
	@Override
	public int compareTo(RoutingEntry entry) {
		int compareID = ((RoutingEntry) entry).getID();
		return this.ID-compareID;
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof RoutingEntry) {
			RoutingEntry entry = (RoutingEntry) o; 
			if(Arrays.equals(IP_address, entry.getIP_address()) && portNumber == entry.portNumber) {
				return true;
			}
		}
		return false;
	}

}
