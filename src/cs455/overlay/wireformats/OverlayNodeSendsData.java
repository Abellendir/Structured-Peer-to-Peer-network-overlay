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
public class OverlayNodeSendsData implements Event, Protocol {

	private int type = OVERLAY_NODE_SENDS_DATA;
	private int destinationId, sourceId, payload, disseminationTraceLength;
	private int[] disseminationNodeIDtrace;

	/**
	 * 
	 * @param type
	 * @param destinationId
	 * @param sourceId
	 * @param payload
	 * @param disseminationTraceLength
	 * @param disseminationNodeIDtrace
	 */
	public OverlayNodeSendsData(int destinationId, int sourceId, int payload, int[] disseminationNodeIDtrace) {
		this.destinationId = destinationId;
		this.sourceId = sourceId;
		this.payload = payload;
		if (disseminationNodeIDtrace == null) {
			disseminationTraceLength = 0;
		} else {
			this.disseminationTraceLength = disseminationNodeIDtrace.length;
		}
		this.disseminationNodeIDtrace = disseminationNodeIDtrace;
	}

	/**
	 * @return
	 */
	public int getDestinationId() {
		return destinationId;
	}

	/**
	 * @return
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * @return
	 */
	public int getPayload() {
		return payload;
	}

	/**
	 * @return
	 */
	public int getDisseminationTraceLength() {
		return disseminationTraceLength;
	}

	/**
	 * @return
	 */
	public int[] getDisseminationNodeIDtrace() {
		return disseminationNodeIDtrace;
	}

	/**
	 * constructor to unmarshall the bytes
	 * 
	 * @param data
	 * @throws IOException
	 */
	public OverlayNodeSendsData(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

		type = din.readByte();
		destinationId = din.readInt();
		sourceId = din.readInt();
		payload = din.readInt();
		disseminationTraceLength = din.readInt();
		if (!(disseminationTraceLength == 0)) {
			disseminationNodeIDtrace = new int[disseminationTraceLength];
			for (int i = 0; i < disseminationTraceLength; i++) {
				disseminationNodeIDtrace[i] = din.readInt();
			}
		} else {
			disseminationNodeIDtrace = null;
		}
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
		dout.writeInt(destinationId);
		dout.writeInt(sourceId);
		dout.writeInt(payload);
		dout.writeInt(disseminationTraceLength);
		for (int i = 0; i < disseminationTraceLength; i++) {
			dout.writeInt(disseminationNodeIDtrace[i]);
		}
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
		return "\nbyte: Message Type; (OVERLAY_NODE_SENDS_DATA)\nint: " + destinationId + "\nint: " + sourceId
				+ "\nint: " + payload + "\nint: " + disseminationTraceLength + "\nint[^^]: "
				+ Arrays.toString(disseminationNodeIDtrace) + "\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs455.overlay.wireformats.Event#getStatus()
	 */
	@Override
	public int getStatus() {
		return destinationId;
	}
}
