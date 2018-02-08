/**
 * 
 */
package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Adam Bellendir
 *
 */
public class RegistryReportsRegistrationStatus implements Event , Protocol{
	
	private byte type = REGISTRY_REPORTS_REGISTRATION_STATUS;
	private int ID = -1;
	private int length;
	private String message;
	
	/**
	 * 
	 */
	public RegistryReportsRegistrationStatus(byte type, int ID, String message) {
		this.type = type;
		this.ID = ID;
		this.message = message;
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public RegistryReportsRegistrationStatus(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		type = din.readByte();
		ID = din.readInt();
		length = din.readInt();
		byte[] infoMessage = new byte[length];
		din.readFully(infoMessage);
		message = new String(infoMessage);
		
		baInputStream.close();
		din.close();
	}

	@Override
	/**
	 * 
	 */
	public byte[] getByte() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeInt(type);
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	}

	@Override
	/**
	 * 
	 */
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	/**
	 * 
	 */
	public String toString() {
		return "byte: Message Type (" + type + ")" +
				"\nint: Success status; " + ID +
				"\nByte: " + length +
				"\nbyte[^^]: " + message + "\n";
	}
}
