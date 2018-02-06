package cs455.overlay.wireformats;

public class OverlayNodeSendsData implements Event {
	
	private byte type;
	private int destinationId;
	private int sourceId;
	private int payload;
	private int disseminationTrace;
	private int[] disseminationNodeIDtrace;

	public OverlayNodeSendsData() {
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
