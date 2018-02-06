package cs455.overlay.wireformats;

public class OverlayNodeReportsTrafficSummary implements Event {
	
	private byte type;
	private int AssignedNodeId;
	private int totalSent;
	private int totalRelayed;
	private long sumSentData;
	private int totalNumPacketsRec;
	private long sumPacketsRec;

	public OverlayNodeReportsTrafficSummary() {
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
