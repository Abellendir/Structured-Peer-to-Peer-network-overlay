/**
 *
 */
package cs455.overlay.transport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import cs455.overlay.wireformats.EventFactory;

/**
 * @Author Adam Bellendir
 */
public class TCPConnection implements Runnable {

	private final Socket socket;
	private final DataOutputStream dout;
	private final DataInputStream din;
	private final EventFactory eventFactory = EventFactory.getInstance();
	private final TCPConnectionsCache cache = TCPConnectionsCache.getInstance();
	private final BlockingQueue<byte[]> buffer = new ArrayBlockingQueue<byte[]>(1000);
	private final byte[] addr;
	private final int port;
	private final TCPSender sender;
	private final TCPReceiver receiver;

	/**
	 *
	 * @throws IOException
	 *
	 */
	public TCPConnection(Socket socket) throws IOException {
		this.socket = socket;
		InetAddress IP_address = socket.getInetAddress();
		addr = IP_address.getAddress();
		System.out.println("Address of incoming Node: " + Arrays.toString(addr));
		port = socket.getPort();
		din = new DataInputStream(socket.getInputStream());
		dout = new DataOutputStream(socket.getOutputStream());
		receiver = new TCPReceiver();
		sender = new TCPSender();
	}

	/**
	 * 
	 * @param message
	 */
	public void addMessage(byte[] message) {
		try {
			buffer.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public byte[] getAddress() {
		return addr;
	}

	/**
	 *
	 */
	public void run() {
		new Thread(receiver).start();
		new Thread(sender).start();/*
		int dataLength;
		while (socket != null) {
			try {
				dataLength = din.readInt();
				byte[] data = new byte[dataLength];
				din.readFully(data, 0, dataLength);
				eventFactory.addMessage(new IncomingMessage(data, addr, port));
			} catch (SocketException se) {
				System.out.println(se.getMessage());
				break;
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
				break;
			}
		}
		cache.remove(this);*/

	}

	public void sendData(byte[] dataToSend) throws IOException {
		// System.out.println("SENDING");
		try {
			buffer.put(dataToSend);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*
		int dataLength = dataToSend.length;
		dout.writeInt(dataLength);
		dout.write(dataToSend, 0, dataLength);
		dout.flush();*/

	}

	public int getPortNumber() {
		return port;
	}

	public String toString() {
		return "\nIP: " + Arrays.toString(addr) + "\nPort: " + port;
	}

	class TCPReceiver implements Runnable {
		
		@Override
		public void run() {
			while (socket != null) {
				try {
					int dataLength = din.readInt();
					byte[] data = new byte[dataLength];
					din.readFully(data, 0, dataLength);
					eventFactory.addMessage(new IncomingMessage(data, addr, port));
				} catch (SocketException se) {
					System.out.println(se.getMessage());
					break;
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
					break;
				}
			}

		}

	}

	class TCPSender implements Runnable {

		@Override
		public void run() {
			while (socket != null) {
				try {
					byte[] dataToSend = buffer.take();
					int dataLength = dataToSend.length;
					dout.writeInt(dataLength);
					dout.write(dataToSend, 0, dataLength);
					dout.flush();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
}
