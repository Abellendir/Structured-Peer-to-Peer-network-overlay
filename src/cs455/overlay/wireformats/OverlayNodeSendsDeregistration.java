package cs455.overlay.wireformats;

public class OverlayNodeSendsDeregistration implements Event {
	
	private byte type;
	private byte length;
	private byte[] IP_address;
	private int portNumber;
	private int assignedID;

	public OverlayNodeSendsDeregistration() {
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
