package cs455.overlay.wireformats;

public class RegistrySendsNodeManifest implements Event {
	
	private byte type;
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
	public byte[] getByte() {
		// TODO Auto-generated method stub
		return null;
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
