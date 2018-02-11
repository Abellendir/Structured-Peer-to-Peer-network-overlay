/**
 * 
 */
package cs455.overlay.routing;

import java.util.ArrayList;
import java.util.Collections;

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
}
