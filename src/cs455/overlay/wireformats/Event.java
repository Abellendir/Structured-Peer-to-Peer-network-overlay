package cs455.overlay.wireformats;

/**
 * 
 * @author Adam Bellendir
 *
 */
public interface Event {
	
	/**
	 * @return 
	 * 
	 */
	byte[] getByte();
	
	/**
	 * 
	 */
	int getType();
	
	/**
	 * 
	 * @return
	 */
	String toString();
	
}
