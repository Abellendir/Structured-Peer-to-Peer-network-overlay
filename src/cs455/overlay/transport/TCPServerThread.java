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
	private TCPConnectionsCache cache = TCPConnectionsCache.getInstance();
	
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
		while(serverSocket != null) {
			try {
				Socket socket = serverSocket.accept();
				TCPConnection tcpConnection = new TCPConnection(socket);
				Thread connection = new Thread(tcpConnection);
				connection.start();
				
				System.out.print("node connects to node\n");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
