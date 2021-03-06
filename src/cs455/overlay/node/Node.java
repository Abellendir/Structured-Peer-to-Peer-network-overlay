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
	
	/**
	 * 
	 * @param command
	 */
	public void interactiveCommandEvent(String command);

	/**
	 * 
	 * @param command
	 */
	void interactiveCommandEvent(String[] command);
	
}
