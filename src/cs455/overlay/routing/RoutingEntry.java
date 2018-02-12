package cs455.overlay.routing;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import cs455.overlay.transport.TCPConnection;

public class RoutingEntry implements Comparable<RoutingEntry>{
	
	private int ID;
	private int index;
	private byte[] IP_address;
	private InetAddress addr;
	private int portNumber;
	private TCPConnection conn;
	private RoutingTable table;
	
	public RoutingEntry(int ID, int index, byte[] IP_address, int portNumber, TCPConnection tcpConnection) {
		this.ID = ID;
		this.index = index;
		this.IP_address = IP_address;
		try {
			this.addr = InetAddress.getByAddress(IP_address);
		} catch (UnknownHostException e) {
			System.out.println("Node (" + ID +") Failed to convert to InetAddress for printing");
			e.printStackTrace();
		}
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
		return "Node ID: (" + ID + ") IP: (" + addr +") Port Number: (" + portNumber + ")";
	}

}
