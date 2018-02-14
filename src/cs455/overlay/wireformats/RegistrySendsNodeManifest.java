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

import cs455.overlay.routing.RoutingEntry;
import cs455.overlay.routing.RoutingTable;

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
	private RoutingEntry entry;
	private int numberEntries;
	
	public RegistrySendsNodeManifest(RoutingEntry entry, int[] allNodes, int numberEntries) {
		this.entry = entry;
		this.allNodes = allNodes;
		this.numberEntries = numberEntries;
		nodeID = new int[numberEntries];
		IP_addresses = new byte[numberEntries][];
		portNumbers = new int[numberEntries];
		for(int i = 0; i < entry.getTable().getSize(); i++) {
			nodeID[i] = entry.getTable().get(i).getID();
			IP_addresses[i] = entry.getTable().get(i).getIP_address();
			portNumbers[i] = entry.getTable().get(i).getPortNumber();
			System.out.println(nodeID[i]);
			System.out.println(Arrays.toString(IP_addresses[i]));
			System.out.println(portNumbers[i]);
		}
	}
	public int[] getNodeID() {
		return nodeID;
	}
	public byte[][] getIP_addresses() {
		return IP_addresses;
	}
	public int[] getPortNumbers() {
		return portNumbers;
	}
	public int[] getAllNodes() {
		return allNodes;
	}
	public RoutingEntry getEntry() {
		return entry;
	}
	public int getNumberEntries() {
		return numberEntries;
	}
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
		numberEntries = size;
		nodeID = new int[size];
		IP_addresses = new byte[size][];
		portNumbers = new int[size];
		for(int i = 0; i < size; i++) {
			int ID = din.readInt();
			nodeID[i] = ID;
			int length = din.readByte();
			IP_addresses[i] = new byte[length];
			din.readFully(IP_addresses[i], 0, length);
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
		/*
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
		*/
		dout.writeByte(type);
		dout.writeByte(numberEntries);
		for(int i = 0; i < numberEntries; i++) {
			int id = nodeID[i];
			dout.writeInt(id);
			int length = this.IP_addresses[i].length;
			dout.writeByte(length);
			dout.write(IP_addresses[i],0,length);
			dout.writeInt(portNumbers[i]);
		}
		dout.writeByte(allNodes.length);
		for(int i: allNodes) {
			dout.writeInt(i);
		}
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		

		System.out.println(Arrays.toString(marshalledBytes));
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
		String string = "";
		string += "\n\nbyte: Message type; " + this.type +
				  "\nbyte: routing table size " + this.nodeID.length;
		for(int i = 0; i < this.nodeID.length; i++) {
			string += "\nint: Node Id of node " +(i+1)+ " hop away: " + nodeID[i] +
			          "\nbyte: " + this.IP_addresses[i].length +
					  "\nbyte[^^]: " + Arrays.toString(this.IP_addresses[i]) +
					  "\nint: " + this.portNumbers[i] ;
		}
		string += "\nbyte: " + this.allNodes.length + 
				"\nint[^^]: " + Arrays.toString(this.allNodes) + "\n\n";
		return string;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}
}
