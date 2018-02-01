package cs455.overlay.wireformats;

import java.util.Arrays;

public class NodeReportsOverlaySetupStatus implements Event {
	
	private byte type;
	private int status;
	private byte length;
	private byte[] informationString;

	public NodeReportsOverlaySetupStatus() {
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
		return "\nbyte: Message Type (" + type + ")" +
				"\nint: Success status; " + status +
				"\nbyte: " + length +
				"\nbyte[^^]: " + Arrays.toString(informationString);
	}	
}
