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
	
	public void add(RoutingEntry entry) {
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
	
	public List<RoutingEntry> getList() {
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
	
	public void remove(int nodeId) {
		for(RoutingEntry entry: routingTable) {
			if(entry.getID()==nodeId) {
				routingTable.remove(entry);
			}
		}
	}
	
	public TCPConnection getConnectionOffID(int nodeID) {
		for(RoutingEntry entry: routingTable) {
			if(entry.getID()==nodeID) {
				return entry.getConnection();
			}
		}
		return null;
	}
	
	public int[] allNodes() {
		int[] all = new int[getSize()];
		for(int i = 0; i < getSize(); i++) {
			all[i] = routingTable.get(i).getID();
		}
		return all;
	}

	public boolean contains(int nodeID) {
		for(RoutingEntry entry: routingTable) {
			if(entry.getID() == nodeID) {
				return true;
			}
		}
		return false;
	}
}
