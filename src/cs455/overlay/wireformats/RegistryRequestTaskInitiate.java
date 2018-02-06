package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistryRequestTaskInitiate implements Event {
	
	private byte type;
	private int numPacketsToSend;

	public RegistryRequestTaskInitiate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] getByte() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		return marshalledBytes;
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
