package cs455.overlay.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Chemical
 *
 */
public class TCPServerThread implements Runnable{
	
	/**
	 * 
	 */
	private ServerSocket serverSocket;
	
	/**
	 * 
	 * @param serverSocket
	 */
	public TCPServerThread(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	/**
	 * 
	 */
	public void run() {
		
		try {
			Socket socket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
