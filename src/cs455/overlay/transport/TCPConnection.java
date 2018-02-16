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
		System.out.println("Port number of incoming Node: " + port);
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
		new Thread(sender).start();
	}

	/**
	 * @param dataToSend
	 * @throws IOException
	 */
	public void sendData(byte[] dataToSend) throws IOException {
		try {
			buffer.put(dataToSend);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public int getPortNumber() {
		return port;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\nIP: " + Arrays.toString(addr) + "\nPort: " + port;
	}

	/**
	 * @author adam_
	 *
	 */
	class TCPReceiver implements Runnable {
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
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

	/**
	 * @author adam_
	 *
	 */
	class TCPSender implements Runnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
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
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}
}
