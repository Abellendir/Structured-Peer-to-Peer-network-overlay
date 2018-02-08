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
public class OverlayNodeSendsDeregistration implements Event , Protocol {
	
	private byte type = OVERLAY_NODE_SENDS_DEREGISTRATION;
	private byte length;
	private byte[] IP_address;
	private int portNumber;
	private int assignedID;

	/**
	 * 
	 */
	public OverlayNodeSendsDeregistration() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public OverlayNodeSendsDeregistration(byte[] data) {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
	}
	
	@Override
	/**
	 * 
	 */
	public byte[] getByte() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
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
		return null;
	}
}
