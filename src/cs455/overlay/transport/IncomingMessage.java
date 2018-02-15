package cs455.overlay.transport;

import java.util.Arrays;

public class IncomingMessage {
	
	private final byte[] marshalledBytes;
	private final byte[] IP_expected;
	private final int connectionPortNumber;
	
	public IncomingMessage(byte[] marshalledBytes, byte[] IP_expected, int connectionPortNumber) {
		this.marshalledBytes = marshalledBytes;
		this.IP_expected = IP_expected;
		this.connectionPortNumber = connectionPortNumber;
	}

	/**
	 * @return the marshalledBytes
	 */
	public byte[] getMarshalledBytes() {
		return marshalledBytes;
	}

	/**
	 * @return the iP_expected
	 */
	public byte[] getIP_expected() {
		return IP_expected;
	}

	/**
	 * @return the connectionPortNumber
	 */
	public int getConnectionPortNumber() {
		return connectionPortNumber;
	}
	
	@Override
	public String toString() {
		String string = "bytes: " + Arrays.toString(marshalledBytes)
						+ "\nIP: " + Arrays.toString(IP_expected)
						+ "\nPort: "+connectionPortNumber;
		return string;
	}
}
