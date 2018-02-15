package cs455.overlay.routing;

import java.util.Arrays;

import cs455.overlay.transport.TCPConnection;

public class RoutingEntry implements Comparable<RoutingEntry>{
	
	private final int ID;
	private int index;
	private final byte[] IP_address;
	private final int portNumber;
	private final TCPConnection conn;
	private RoutingTable table;
	
	public RoutingEntry(int ID, byte[] IP_address, int portNumber, TCPConnection tcpConnection){
		this.ID = ID;
		this.IP_address = IP_address;
		this.portNumber = portNumber;
		this.conn = tcpConnection;
	}
	
	public void removeTable() {
		table = null;
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
	
	public boolean equals(int id) {
		return id==ID;
	}
	
	public void setTable(RoutingTable table) {
		this.table = table;
	}
	
	public RoutingTable getTable() {
		return table;
	}
	
	@Override
	public String toString() {
		return "Node ID: (" + ID + ") IP: (" + Arrays.toString(IP_address) +") Port Number: (" + portNumber + ")";
	}

}
