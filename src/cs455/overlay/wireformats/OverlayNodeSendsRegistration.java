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
import java.util.Arrays;

/**
 * 
 * @author Adam Bellendir
 *
 */
public class OverlayNodeSendsRegistration implements Event, Protocol {
	
	private int type = OVERLAY_NODE_SENDS_REGISTRATION;
	private int length;
	private byte[] IP_address;
	private byte[] socketAddress;
	private int portNumber;
	private int status = 0;
	private int socketPort;
	
	/**
	 * 
	 * @return
	 */
	public int getLength() {
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
	public OverlayNodeSendsRegistration(int length, byte[] IP_address, int portNumber) {
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
		din.readFully(IP_address,0,length);
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
		dout.write(IP_address,0,length);
		dout.writeInt(portNumber);
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
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus() {
		this.status = -1;
	}
	
	@Override
	/**
	 * 
	 */
	public String toString() {
		return "\nbyte: Message Type (" + type + ")" +
				"\nbyte: length of folling IP address field " + length +
				"\nbyte[^^]: IP address; " + Arrays.toString(IP_address) +
				"\nint: " + portNumber + "\n";
	}

	public void setSocketPort(int port) {
		this.socketPort = port;
		
	}

	public int getSocketPort() {
		return socketPort;
	}

	public byte[] getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(byte[] socketAddress) {
		this.socketAddress = socketAddress;
	}

}
