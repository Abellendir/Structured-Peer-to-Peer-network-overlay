/**
 * 
 */
package cs455.overlay.routing;

import java.util.ArrayList;
import java.util.Collections;

import cs455.overlay.transport.TCPConnection;

/**
 * @author Adam Bellendir
 *
 */
public class RoutingTable {

	private ArrayList<RoutingEntry> routingTable = new ArrayList<RoutingEntry>();
	
	/**
	 * 
	 */
	public RoutingTable() {
	}
	
	public synchronized void add(RoutingEntry entry) {
		routingTable.add(entry);
	}
	
	public RoutingEntry get(int index) {
		return routingTable.get(index);
	}
	
	public int getSize() {
		return routingTable.size();
	}
	
	public void sort() {
		Collections.sort(routingTable);
	}
	
	public boolean contains(RoutingEntry entry) {
		return routingTable.contains(entry);
	}
	
	public ArrayList<RoutingEntry> getList() {
		return routingTable;
	}
	
	@Override
	public String toString() {
		String table = "";
		for(int i = 0; i < routingTable.size(); i++) {
			table += "\n" + routingTable.get(i);
		}
		return table;
	}

	public TCPConnection getConnectionOffID(int nodeID) {
		for(RoutingEntry entry: routingTable) {
			if(entry.getID()==nodeID) {
				return entry.getConnection();
			}
		}
		return null;
	}
}
