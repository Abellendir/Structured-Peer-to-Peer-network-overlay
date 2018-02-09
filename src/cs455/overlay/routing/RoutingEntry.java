package cs455.overlay.routing;

public class RoutingEntry {
	
	private int ID;
	private int index;
	private byte[] IP_address;
	private int portNumber;
	
	public RoutingEntry(int ID, int index, byte[] IP_address, int portNumber) {
		
		this.ID = ID;
		this.index = index;
		this.IP_address = IP_address;
		this.portNumber = portNumber;
	}

	public int getID() {
		return ID;
	}

	public int getIndex() {
		return index;
	}

	public byte[] getIP_address() {
		return IP_address;
	}

	public int getPortNumber() {
		return portNumber;
	}

}
