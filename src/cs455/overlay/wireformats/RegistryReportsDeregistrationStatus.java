package cs455.overlay.wireformats;

public  class RegistryReportsDeregistrationStatus implements Event{
	
	private byte type;
	private byte lengthIPAddress;
	private byte[] IP_address;
	private int portNumber;
	private int assignedNodeID;

	public RegistryReportsDeregistrationStatus() {
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
