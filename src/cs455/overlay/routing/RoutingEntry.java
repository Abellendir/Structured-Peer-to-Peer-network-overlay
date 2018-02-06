package cs455.overlay.routing;

public class RoutingEntry {
	
	private int ID;
	private byte length;
	private byte[] IP_address;
	private int portNumber;
	
	public RoutingEntry(int ID, byte length, byte[] IP_address, int portNumber) {
		
		this.ID = ID;
		this.length = length;
		this.IP_address = IP_address;
		this.portNumber = portNumber;
	}

	public int getID() {
		return ID;
	}

	public byte getLength() {
		return length;
	}

	public byte[] getIP_address() {
		return IP_address;
	}

	public int getPortNumber() {
		return portNumber;
	}

}
