/**
 * 
 */
package cs455.overlay.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs455.overlay.transport.TCPConnection;

/**
 * @author Adam Bellendir
 *
 */

public class RoutingTable {

	//private ArrayList<RoutingEntry> routingTable = new ArrayList<RoutingEntry>();
	private List<RoutingEntry> routingTable = Collections.synchronizedList(new ArrayList<RoutingEntry>());
	
	/**
	 * 
	 */
	public RoutingTable() {
	}
	
	/**
	 * @param entry
	 */
	public void add(RoutingEntry entry) {
		routingTable.add(entry);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public RoutingEntry get(int index) {
		return routingTable.get(index);
	}
	
	/**
	 * @return
	 */
	public int getSize() {
		return routingTable.size();
	}
	
	/**
	 * 
	 */
	public void sort() {
		Collections.sort(routingTable);
	}
	
	/**
	 * @param entry
	 * @return
	 */
	public boolean contains(RoutingEntry entry) {
		return routingTable.contains(entry);
	}
	
	/**
	 * @return
	 */
	public List<RoutingEntry> getList() {
		return routingTable;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String table = "";
		for(int i = 0; i < routingTable.size(); i++) {
			table += "\n" + routingTable.get(i);
		}
		return table;
	}
	
	/**
	 * @param nodeId
	 */
	public void remove(int nodeId) {
		for(int i = 0; i < getSize(); i++) {
			if(routingTable.get(i).getID()==nodeId)
				routingTable.remove(i);
		}
	}
	
	/**
	 * @param nodeID
	 * @return
	 */
	public TCPConnection getConnection(int nodeID) {
		for(RoutingEntry entry: routingTable) {
			if(entry.getID()==nodeID) {
				return entry.getConnection();
			}
		}
		return null;
	}
	
	/**
	 * @return
	 */
	public int[] allNodes() {
		int[] all = new int[getSize()];
		for(int i = 0; i < getSize(); i++) {
			all[i] = routingTable.get(i).getID();
		}
		return all;
	}

	/**
	 * @param nodeID
	 * @return
	 */
	public boolean contains(int nodeID) {
		for(RoutingEntry entry: routingTable) {
			if(entry.getID() == nodeID) {
				return true;
			}
		}
		return false;
	}
}
