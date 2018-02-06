package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeSendsData implements Event {
	
	private byte type;
	private int destinationId;
	private int sourceId;
	private int payload;
	private int disseminationTraceLength;
	private int[] disseminationNodeIDtrace;

	public OverlayNodeSendsData(int type, int destinationId, int sourceId, 
			int payload,int disseminationTraceLength,int[] disseminationNodeIDtrace ) {
		this.type = (byte) type;
		this.destinationId = destinationId;
		this.sourceId = sourceId;
		this.payload = payload;
		this.disseminationTraceLength = disseminationTraceLength;
		this.disseminationNodeIDtrace = disseminationNodeIDtrace;
	}

	@Override
	public byte[] getByte() throws IOException{
		// TODO Auto-generated method stub
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.write(type);
		dout.writeByte(destinationId);
		dout.writeByte(sourceId);
		dout.writeByte(payload);
		dout.writeByte(disseminationTraceLength);
		byte[] nodeIDTrace = new byte[disseminationTraceLength];
		for(int i = 0; i < disseminationTraceLength; i++) {
			nodeIDTrace[i] = (byte) disseminationNodeIDtrace[i];
		}
		dout.write(nodeIDTrace);
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
		return null;
	}
}
