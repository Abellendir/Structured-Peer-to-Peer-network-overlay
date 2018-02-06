package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistrySendsNodeManifest implements Event {
	
	private byte type = 6;
	private byte routingTableSize;
	private int[] nodeID;
	private byte[] lengthIP;
	private byte[][] nodeIPaddress;
	private int[] portNumberOfID;
	private byte numNodes;
	private int[] allNodeIDs;

	public RegistrySendsNodeManifest() {
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
