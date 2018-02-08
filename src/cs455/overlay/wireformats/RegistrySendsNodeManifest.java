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
public class RegistrySendsNodeManifest implements Event, Protocol {
	
	private byte type = REGISTRY_SENDS_NODE_MANIFEST;
	private byte size;
	private int[] nodeID;
	private byte[] length;
	private byte[][] IP_addresses;
	private int[] portNumbers;
	private byte numberNodesInSystem;
	private int[] allNodes;

	/**
	 * constructor to construct the outgoing message
	 */
	public RegistrySendsNodeManifest(byte size, int[] nodeID, byte[] length, byte[][] IP_addresses, 
			int[] portNumber, byte numberNodesInSystem, int[] allNodes ) {
		this.size = size;
		this.nodeID = nodeID;
		this.length = length;
		this.IP_addresses = IP_addresses;
		this.portNumbers = portNumber;
		this.numberNodesInSystem = numberNodesInSystem;
		this.allNodes = allNodes;
	}
	
	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public RegistrySendsNodeManifest(byte[] data) {
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
		
		dout.writeByte(type);
		dout.writeByte(size);
		for(byte i = 0; i < size; i++) {
			dout.writeInt(nodeID[i]);
			dout.writeByte(length[i]);
			dout.write(IP_addresses[i]);
			dout.writeInt(portNumbers[i]);
		}
		
		dout.writeByte(numberNodesInSystem);
		for(byte i = 0; i < numberNodesInSystem; i++) {
			dout.writeInt(allNodes[i]);
		}
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
		return null;
	}
}
