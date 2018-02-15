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
 * 
 * @author Adam Bellendir
 *
 */
public  class RegistryReportsDeregistrationStatus implements Event, Protocol{
	
	private int type = REGISTRY_REPORTS_DEREGISTRATION_STATUS;
	private int ID;
	private int length;
	private String message;

	public void setMessage() {
		
	}
	/**
	 * 
	 */
	public RegistryReportsDeregistrationStatus(int ID, String message) {
		this.ID = ID;
		this.message = message;
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public RegistryReportsDeregistrationStatus(byte[] data) throws IOException{
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
		return "\nbyte: Message type" + this.type +
				"\nint: " + this.getStatus() +
				"\nbyte: " + this.length +
				"\nbyte[^^}" + message + "\n";
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
