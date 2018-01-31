package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

public class OverlayNodeSendsRegistration implements Event {
	
	private byte type = 2;
	private byte length;
	private byte[] IP_address;
	private int portNumber;
	
	/**
	 * 
	 * @param length
	 * @param IP_address
	 * @param portNumber
	 */
	public OverlayNodeSendsRegistration(byte length, byte[] IP_address, int portNumber) {
		this.IP_address = IP_address;
		this.portNumber = portNumber;
	}

	@Override
	public byte[] getByte() throws IOException {
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(type);
		
		return marshalledBytes;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	@Override
	public String toString() {
		return null;
	}

}
