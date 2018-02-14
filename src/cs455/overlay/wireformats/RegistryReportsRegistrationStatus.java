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
	
	private int type = REGISTRY_REPORTS_REGISTRATION_STATUS;
	private int ID = -1;
	private int length;
	private String message;
	private boolean debug = true;
	/**
	 * 
	 */
	public RegistryReportsRegistrationStatus(int ID, String message) {
		this.ID = ID;
		this.message = message;
		this.length = message.length();
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public RegistryReportsRegistrationStatus(byte[] data) throws IOException {
		if(debug) System.out.println("Entering Constructor");
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		type = din.readByte();
		ID = din.readInt();
		length = din.readByte();
		byte[] infoMessage = new byte[length];
		din.readFully(infoMessage,0,length);
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
		
		dout.writeByte(type);
		dout.writeInt(ID);
		byte[] messagebytes = message.getBytes();
		int length = messagebytes.length;
		dout.writeByte(length);
		dout.write(messagebytes);
		
		
		dout.flush();
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
		return type;
	}
	
	@Override
	/**
	 * 
	 */
	public String toString() {
		return "\n\nbyte: Message Type (" + type + ")" +
				"\nint: Success status; " + ID +
				"\nByte: " + length +
				"\nbyte[^^]: " + message + "\n\n";
	}

	@Override
	public int getStatus() {
		return ID;
	}
}
