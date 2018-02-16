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

	private int type = NODE_REPORTS_OVERLAY_SETUP_STATUS;
	private int status;
	private String informationString;

	/**
	 * 
	 * @param status
	 * @param length
	 * @param informationString
	 */
	public NodeReportsOverlaySetupStatus(int status, String informationString) {
		this.status = status;
		this.informationString = informationString;
	}

	/**
	 * constructor to unmarshall the bytes
	 * 
	 * @param data
	 * @throws IOException
	 */
	public NodeReportsOverlaySetupStatus(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

		type = din.readByte();
		status = din.readInt();
		int length = din.readByte();
		byte[] message = new byte[length];
		din.readFully(message);
		informationString = new String(message);

		baInputStream.close();
		din.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs455.overlay.wireformats.Event#getByte()
	 */
	@Override
	public byte[] getByte() throws IOException {
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

		dout.writeByte(type);
		dout.writeInt(status);
		byte[] informationMessage = informationString.getBytes();
		int length = informationMessage.length;
		dout.writeByte(length);
		dout.write(informationMessage);

		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();

		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs455.overlay.wireformats.Event#getType()
	 */
	@Override
	public int getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\nbyte: Message Type (NODE_REPORTS_OVERLAY_SETUP_STATUS)" + "\nint: Success status; " + this.status
				+ "\nbyte: " + this.informationString.length() + "\nbyte[^^]: " + this.informationString + "\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs455.overlay.wireformats.Event#getStatus()
	 */
	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

}
