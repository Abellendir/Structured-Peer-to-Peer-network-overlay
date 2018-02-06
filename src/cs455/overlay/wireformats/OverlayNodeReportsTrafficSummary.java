package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeReportsTrafficSummary implements Event {
	
	private byte type;
	private int AssignedNodeId;
	private int totalSent;
	private int totalRelayed;
	private long sumSentData;
	private int totalNumPacketsRec;
	private long sumPacketsRec;

	public OverlayNodeReportsTrafficSummary() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] getByte() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		return marshalledBytes;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		return null;
	}

}
