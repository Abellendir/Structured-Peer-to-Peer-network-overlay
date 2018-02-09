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
	
	private int type = OVERLAY_NODE_SENDS_DEREGISTRATION;
	private int length;
	private byte[] IP_address;
	private int portNumber;
	private int nodeID;

	/**
	 * 
	 */
	public OverlayNodeSendsDeregistration(byte[] IP_address, int portNumber, int nodeID) {
		this.length = IP_address.length;
		this.portNumber = portNumber;
		this.nodeID = nodeID;
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public OverlayNodeSendsDeregistration(byte[] data) throws IOException{
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		type = din.readByte();
		length = din.readByte();
		IP_address = new byte[length];
		din.readFully(IP_address,0,length);
		portNumber = din.readInt();
		nodeID = din.readInt();
		
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
		dout.writeByte(length);
		dout.write(IP_address,0,length);
		dout.writeInt(portNumber);
		dout.writeInt(nodeID);
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
		return type;
	}

	@Override
	/**
	 * 
	 */
	public String toString() {
		return null;
	}
}
