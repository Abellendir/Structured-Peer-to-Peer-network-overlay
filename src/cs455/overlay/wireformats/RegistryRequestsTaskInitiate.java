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
public class RegistryRequestsTaskInitiate implements Event, Protocol {

	private int type = REGISTRY_REQUESTS_TASK_INITIATE;
	private int numPacketsToSend;

	/**
	 * 
	 */
	public RegistryRequestsTaskInitiate(int numPacketsToSend) {
		this.numPacketsToSend = numPacketsToSend;
	}

	/**
	 * constructor to unmarshall the bytes
	 * 
	 * @param data
	 */
	public RegistryRequestsTaskInitiate(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

		din.readByte();
		numPacketsToSend = din.readInt();

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
		dout.writeInt(numPacketsToSend);

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
		return "\nbyte: Message type; (REGISTRY_REQUESTS_TASK_INITIATE)" + "\nint: " + numPacketsToSend + "\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs455.overlay.wireformats.Event#getStatus()
	 */
	@Override
	public int getStatus() {
		return numPacketsToSend;
	}

}
