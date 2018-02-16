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
public class RegistryRequestsTrafficSummary implements Event, Protocol {

	private int type = REGISTRY_REQUEST_TRAFFIC_SUMMARY;

	/**
	 * 
	 */
	public RegistryRequestsTrafficSummary() {
	}

	/**
	 * constructor to unmarshall the bytes
	 * 
	 * @param data
	 * @throws IOException
	 */
	public RegistryRequestsTrafficSummary(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

		type = din.readByte();

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
		return "\nbyte: (REGISTRY_REQUESTS_TRAFFIC_SUMMARY)\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs455.overlay.wireformats.Event#getStatus()
	 */
	@Override
	public int getStatus() {
		return 0;
	}
}
