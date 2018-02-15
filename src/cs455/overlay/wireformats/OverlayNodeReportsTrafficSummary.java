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
public class OverlayNodeReportsTrafficSummary implements Event, Protocol {
	
	private int type = OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY;
	private int assignedNodeId;
	private int totalSent;
	private int totalRelayed;
	private long sumSentData;
	private int totalNumPacketsRec;
	private long sumPacketsRec;
	


	public int getTotalSent() {
		return totalSent;
	}

	public int getTotalRelayed() {
		return totalRelayed;
	}

	public long getSumSentData() {
		return sumSentData;
	}

	public int getTotalNumPacketsRec() {
		return totalNumPacketsRec;
	}

	public long getSumPacketsRec() {
		return sumPacketsRec;
	}

	/**
	 * 
	 * @param assignedNodeId
	 * @param totalSent
	 * @param totalRelayed
	 * @param sumSentData
	 * @param totalNumPacketsRec
	 * @param sumPacketsRec
	 */
	public OverlayNodeReportsTrafficSummary(int assignedNodeId, int totalSent, int totalRelayed, 
			long sumSentData, int totalNumPacketsRec, long sumPacketsRec) {
		this.assignedNodeId = assignedNodeId;
		this.totalSent = totalSent;
		this.totalRelayed = totalRelayed;
		this.sumSentData = sumSentData;
		this.totalNumPacketsRec = totalNumPacketsRec;
		this.sumPacketsRec = sumPacketsRec;
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 * @throws IOException 
	 */
	public OverlayNodeReportsTrafficSummary(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		type = din.readByte();
		assignedNodeId = din.readInt();
		totalSent = din.readInt();
		totalRelayed = din.readInt();
		sumSentData = din.readLong();
		totalNumPacketsRec = din.readInt();
		sumPacketsRec = din.readLong();
		
		baInputStream.close();
		din.close();
	
		
	}
	
	@Override
	/**
	 * 
	 */
	public byte[] getByte() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(type);
		dout.writeInt(assignedNodeId);
		dout.writeInt(totalSent);
		dout.writeInt(totalRelayed);
		dout.writeLong(sumSentData);
		dout.writeInt(totalNumPacketsRec);
		dout.writeLong(sumPacketsRec);
		
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
		return "\nbyte: Message Type; " + this.type +
				"\nint: " + this.assignedNodeId + 
				"\nint: " + this.totalSent +
				"\nint: " + this.totalRelayed +
				"\nlong: " + this.sumSentData +
				"\nint: " + this.totalNumPacketsRec +
				"\nlong: " + this.sumPacketsRec + "\n";
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return assignedNodeId;
	}

}
