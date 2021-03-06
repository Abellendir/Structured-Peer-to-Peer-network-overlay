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
public class OverlayNodeReportsTaskFinished implements Event, Protocol {

	private int type = OVERLAY_NODE_REPORTS_TASK_FINISHED;
	private int length, portNumber, nodeID;
	private byte[] IP_address;

	/**
	 * 
	 * @param length
	 * @param IP_address
	 * @param portNumber
	 * @param nodeID
	 */
	public OverlayNodeReportsTaskFinished(int length, byte[] IP_address, int portNumber, int nodeID) {
		this.length = IP_address.length;
		this.IP_address = IP_address;
		this.portNumber = portNumber;
		this.nodeID = nodeID;
	}

	/**
	 * constructor to unmarshall the bytes
	 * 
	 * @param data
	 */
	public OverlayNodeReportsTaskFinished(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

		type = din.readByte();
		length = din.readByte();
		IP_address = new byte[length];
		din.readFully(IP_address, 0, length);
		portNumber = din.readInt();
		nodeID = din.readInt();

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
		dout.writeByte(length);
		dout.write(IP_address, 0, length);
		dout.writeInt(portNumber);
		dout.writeInt(nodeID);

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
		return "\nbyte: Message type; (OVERLAY_NODE_REPORTS_TASK_FINISHED)\nbyte: " + this.length + "\nbyte[^^]: "
				+ Arrays.toString(this.IP_address) + "\nint: " + this.portNumber + "\nint: " + this.nodeID + "\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs455.overlay.wireformats.Event#getStatus()
	 */
	@Override
	public int getStatus() {
		return nodeID;
	}
}
