package cs455.overlay.wireformats;

import java.io.IOException;

/**
 * 
 * @author Adam Bellendir
 *
 */
public interface Event {

	/**
	 * @return 
	 * @throws IOException 
	 * 
	 */
	byte[] getByte() throws IOException;
	
	
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
