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

/**
 * @author Adam Bellendir
 *
 */
public class RegistryReportsRegistrationStatus implements Event , Protocol{
	
	private int type = REGISTRY_REPORTS_REGISTRATION_STATUS;
	private int ID = -1;
	private int length;
	private String message;
	
	/**
	 * 
	 */
	public RegistryReportsRegistrationStatus(int ID, String message) {
		this.ID = ID;
		this.message = message;
		this.length = message.length();
	}

	/**
	 * constructor to unmarshall the bytes
	 * @param data
	 */
	public RegistryReportsRegistrationStatus(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		type = din.readByte();
		System.out.println("byte: " + type);
		ID = din.readInt();
		System.out.println("id: " + ID);
		length = din.readByte();
		System.out.println("length: " + length);
		byte[] infoMessage = new byte[length];
		din.readFully(infoMessage,0,length);
		message = new String(infoMessage);
		System.out.println("message: " + message);
		
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
		
		dout.writeByte(type);
		System.out.println("byte: type; " + type);
		dout.writeInt(ID);
		System.out.println("int: Success status; " + ID);
		byte[] messagebytes = message.getBytes();
		int length = messagebytes.length;
		System.out.println("byte: lenght; " + length);
		System.out.println("byte[^^]: " + Arrays.toString(messagebytes));
		dout.writeByte(length);
		dout.write(messagebytes);
		
		
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
		// TODO Auto-generated method stub
		return type;
	}
	
	@Override
	/**
	 * 
	 */
	public String toString() {
		return "byte: Message Type (" + type + ")" +
				"\nint: Success status; " + ID +
				"\nByte: " + length +
				"\nbyte[^^]: " + message + "\n";
	}
}
