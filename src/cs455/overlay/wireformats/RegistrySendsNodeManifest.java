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
	
	private int type = REGISTRY_SENDS_NODE_MANIFEST;
	private int[] nodeID;
	private byte[][] IP_addresses;
	private int[] portNumbers;
	private int[] allNodes;

	/**
	 * constructor to construct the outgoing message
	 */
	public RegistrySendsNodeManifest(int[] nodeID, byte[][] IP_addresses, int[] portNumber, int[] allNodes ) {
		this.nodeID = nodeID;
		this.IP_addresses = IP_addresses;
		this.portNumbers = portNumber;
		this.allNodes = allNodes;
	}
	
	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 * @throws IOException 
	 */
	public RegistrySendsNodeManifest(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		int type = din.readByte();
		int size = din.readByte();
		nodeID = new int[size];
		IP_addresses = new byte[size][];
		portNumbers = new int[size];
		for(int i = size; i < size; i++) {
			nodeID[i] = din.readInt();
			int length = din.readByte();
			din.read(IP_addresses[i],0,length);
			portNumbers[i] = din.readInt();
		}
		int numberNodesInSystem = din.readByte();
		allNodes = new int[numberNodesInSystem];
		for(int i = 0; i < numberNodesInSystem; i++) {
			allNodes[i] = din.readInt();
		}
		
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
		dout.writeByte(nodeID.length);
		for(byte i = 0; i < nodeID.length; i++) {
			dout.writeInt(nodeID[i]);
			int length = IP_addresses[i].length;
			dout.write(IP_addresses[i],0,length);
			dout.writeInt(portNumbers[i]);
		}
		int numberNodesInSystem = allNodes.length; 
		for(byte i = 0; i < numberNodesInSystem; i++) {
			dout.writeInt(allNodes[i]);
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
		return null;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}
}
