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
public class OverlayNodeReportsTaskFinished implements Event, Protocol {
	
	private byte type = OVERLAY_NODE_REPORTS_TASK_FINISHED;
	private byte length;
	private byte[] IP_address;
	private int portNumber;
	private int nodeID;

	/**
	 * 
	 * @param length
	 * @param IP_address
	 * @param portNumber
	 * @param nodeID
	 */
	public OverlayNodeReportsTaskFinished(byte length, byte[] IP_address, int portNumber, int nodeID) {
		this.length = length;
		this.IP_address = IP_address;
		this.portNumber = portNumber;
		this.nodeID = nodeID;
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public OverlayNodeReportsTaskFinished(byte[] data) {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
	}
	
	@Override
	/**
	 * 
	 */
	public byte[] getByte() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	/**
	 * 
	 */
	public String toString() {
		return null;
	}
}
