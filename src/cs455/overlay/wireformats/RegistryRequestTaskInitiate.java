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
public class RegistryRequestTaskInitiate implements Event, Protocol {
	
	private int type = REGISTRY_REQUESTS_TASK_INITIATE;
	private int numPacketsToSend;
	
	/**
	 * 
	 */
	public RegistryRequestTaskInitiate(int numPacketsToSend) {
		this.numPacketsToSend = numPacketsToSend;
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public RegistryRequestTaskInitiate(byte[] data) throws IOException{
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		din.readByte();
		numPacketsToSend = din.readInt();
		
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
		dout.writeByte(numPacketsToSend);
		
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
		return "\nbyte: Message type; " + type +
				"\nint: " + numPacketsToSend;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return numPacketsToSend;
	}

}
