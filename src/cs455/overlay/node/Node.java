/**
 *
 */
package cs455.overlay.node;

import cs455.overlay.wireformats.Event;

/**
 * @author Adam Bellendir
 */
public interface Node {

	/**
	 * 
	 * @param event
	 */
	public void onEvent(Event event);
	
}
