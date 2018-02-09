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
public class OverlayNodeSendsRegistration implements Event, Protocol {
	
	private byte type = OVERLAY_NODE_SENDS_REGISTRATION;
	private byte length;
	private byte[] IP_address;
	private int portNumber;
	
	/**
	 * 
	 * @return
	 */
	public byte getLength() {
		return length;
	}
	
	/**
	 * 
	 * @return
	 */
	public byte[] getIP_address() {
		return IP_address;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPortNumber() {
		return portNumber;
	}
	
	/**
	 * 
	 * @param length
	 * @param IP_address
	 * @param portNumber
	 */
	public OverlayNodeSendsRegistration(byte length, byte[] IP_address, int portNumber) {
		this.length = length;
		this.IP_address = IP_address;
		this.portNumber = portNumber;
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public OverlayNodeSendsRegistration(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		type = din.readByte();
		length = din.readByte();
		IP_address = new byte[length];
		din.readFully(IP_address);
		portNumber = din.readInt();
		
		baInputStream.close();
		din.close();
	}

	@Override
	/**
	 * 
	 */
	public byte[] getByte() throws IOException {
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(type);
		dout.writeByte(length);
		dout.write(IP_address);
		dout.writeByte(portNumber);
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
		return "byte: Message Type (" + type + ")" +
				"\nbyte: length of folling IP address field " + length +
				"\nbyte[^^]: IP address; " + IP_address +
				"\nint: " + portNumber;
	}

}
