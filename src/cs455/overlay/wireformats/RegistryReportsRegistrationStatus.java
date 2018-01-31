/**
 * 
 */
package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Adam Bellendir
 *
 */
public class RegistryReportsRegistrationStatus implements Event{
	
	private int type = 3;
	private int successStatus;
	private int length;
	
	/**
	 * 
	 */
	public RegistryReportsRegistrationStatus() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] getByte() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeInt(type);
		
		return null;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		return null;
	}
}
