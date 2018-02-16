package cs455.overlay.routing;

import java.util.Arrays;

import cs455.overlay.transport.TCPConnection;

/**
 * @author adam_
 *
 */
public class RoutingEntry implements Comparable<RoutingEntry> {

	private final int ID;
	private final byte[] IP_address;
	private final int portNumber;
	private final TCPConnection conn;
	
	/**
	 * primarily used in Registry class
	 */
	private RoutingTable table;

	/**
	 * @param ID
	 * @param IP_address
	 * @param portNumber
	 * @param tcpConnection
	 */
	public RoutingEntry(int ID, byte[] IP_address, int portNumber, TCPConnection tcpConnection) {
		this.ID = ID;
		this.IP_address = IP_address;
		this.portNumber = portNumber;
		this.conn = tcpConnection;
	}

	/**
	 * 
	 */
	public void removeTable() {
		table = null;
	}

	/**
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return
	 */
	public byte[] getIP_address() {
		return IP_address;
	}

	/**
	 * @return
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/**
	 * @return
	 */
	public TCPConnection getConnection() {
		return conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(RoutingEntry entry) {
		int compareID = ((RoutingEntry) entry).getID();
		return this.ID - compareID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof RoutingEntry) {
			RoutingEntry entry = (RoutingEntry) o;
			if (Arrays.equals(IP_address, entry.getIP_address()) && portNumber == entry.portNumber) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean equals(int id) {
		return id == ID;
	}

	/**
	 * @param table
	 */
	public void setTable(RoutingTable table) {
		this.table = table;
	}

	/**
	 * @return
	 */
	public RoutingTable getTable() {
		return table;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node ID: (" + ID + ") IP: (" + Arrays.toString(IP_address) + ") Port Number: (" + portNumber + ")";
	}

}
