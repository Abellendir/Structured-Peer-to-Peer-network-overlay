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
public class NodeReportsOverlaySetupStatus implements Event, Protocol {
	
	private byte type = NODE_REPORTS_OVERLAY_SETUP_STATUS;
	private int status;
	private byte length;
	private String informationString;

	/**
	 * 
	 * @param status
	 * @param length
	 * @param informationString
	 */
	public NodeReportsOverlaySetupStatus(int status, byte length, String informationString) {
		this.status = status;
		this.length = length;
		this.informationString = informationString;
	}
	
	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 * @throws IOException 
	 */
	public NodeReportsOverlaySetupStatus(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		type = din.readByte();
		status = din.readInt();
		length = din.readByte();
		byte[] message = new byte[length];
		din.readFully(message);
		informationString = new String(message);
		
		baInputStream.close();
		din.close();
	}

	@Override
	public byte[] getByte() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(type);
		dout.writeInt(status);
		dout.writeByte(length);
		byte[] informationMessage = informationString.getBytes();
		dout.write(informationMessage);
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	}

	@Override
	public int getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "\nbyte: Message Type (" + type + ")" +
				"\nint: Success status; " + status +
				"\nbyte: " + length +
				"\nbyte[^^]: " + informationString;
	}	
}
