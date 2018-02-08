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
	
	private byte type = OVERLAY_NODE_SENDS_DATA;
	private int destinationId;
	private int sourceId;
	private int payload;
	private int disseminationTraceLength;
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
	public OverlayNodeSendsData(int type, int destinationId, int sourceId, 
			int payload,int disseminationTraceLength,int[] disseminationNodeIDtrace ) {
		this.type = (byte) type;
		this.destinationId = destinationId;
		this.sourceId = sourceId;
		this.payload = payload;
		this.disseminationTraceLength = disseminationTraceLength;
		this.disseminationNodeIDtrace = disseminationNodeIDtrace;
	}

	/**
	 * constructor to unmarshall the bytes
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
		disseminationNodeIDtrace = new int[disseminationTraceLength];
		for(int i = 0; i < disseminationTraceLength; i++) {
			disseminationNodeIDtrace[i] = din.readInt();
		}
		baInputStream.close();
		din.close();
		
	}
	
	@Override
	/**
	 * 
	 */
	public byte[] getByte() throws IOException{
		// TODO Auto-generated method stub
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(type);
		dout.writeInt(destinationId);
		dout.writeInt(sourceId);
		dout.writeInt(payload);
		dout.writeInt(disseminationTraceLength);
		for(int i = 0; i < disseminationTraceLength; i++) {
			dout.writeInt(disseminationNodeIDtrace[i]);
		}
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
		return "\nbyte: Message Type; " + type + 
				"\nint: " + destinationId + 
				"\nint: " + sourceId +
				"\nint: " + payload +
				"\nint: " + disseminationTraceLength +
				"\nint[^^]: " + Arrays.toString(disseminationNodeIDtrace) +
				"\n";
	}
}
