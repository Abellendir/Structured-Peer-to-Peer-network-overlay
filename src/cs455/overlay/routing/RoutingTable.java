/**
 * 
 */
package cs455.overlay.routing;

/**
 * @author Adam Bellendir
 *
 */
public class RoutingTable {

	private RoutingEntry[] routingTable;
	
	/**
	 * 
	 */
	public RoutingTable(int numberOfEntries) {
		routingTable = new RoutingEntry[numberOfEntries];
	}
	
	public void add(int index, RoutingEntry entry) {
		routingTable[index] = entry;
	}
	
	public RoutingEntry get(int index) {
		return routingTable[index];
	}

}
