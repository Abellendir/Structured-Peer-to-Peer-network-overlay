/**
 * 
 */
package cs455.overlay.wireformats;

/**
 * 
 * Bridge between nodes
 * @author Chemical
 *
 */
public class EventFactory {

	private static EventFactory eventFactory = null;

	/**
	 * 
	 */
	private EventFactory() {

	}

	/**
	 *
	 * @return
	 */
	public static EventFactory getInstance() {

		if(eventFactory == null){
			eventFactory = new EventFactory();
		}

		return eventFactory;
	}

}
