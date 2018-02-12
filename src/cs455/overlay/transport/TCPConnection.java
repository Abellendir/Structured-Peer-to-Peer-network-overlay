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

import cs455.overlay.wireformats.EventFactory;

/**
 * @Author Adam Bellendir
 */
public class TCPConnection implements Runnable{

	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;
	private EventFactory eventFactory = EventFactory.getInstance();
	private TCPConnectionsCache cache = TCPConnectionsCache.getInstance();
	private byte[] addr;
	private int port;
	
    /**
     *
     * @throws IOException 
     *
     */
	public TCPConnection(Socket socket) throws IOException {
        this.socket = socket;
        InetAddress IP_address = socket.getInetAddress();
        addr = IP_address.getAddress();
        port = socket.getPort();
        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());
	}
	
	public byte[] getAddress() {
		return addr;
	}
	
    /**
     *
     */
    public void run() {
        int dataLength;
        while(socket != null){
        	System.out.println("Recieved");
            try{
                dataLength = din.readInt();
                byte[] data = new byte[dataLength];
                din.readFully(data,0,dataLength);
                eventFactory.handleBytes(data,addr);

            }catch(SocketException se){
                System.out.println(se.getMessage());
                break;
            }catch(IOException ioe){
                System.out.println(ioe.getMessage());
                break;
            }
        }
        cache.remove(this);

    }

	public void sendData(byte[] dataToSend) throws IOException{
    	int dataLength = dataToSend.length;
    	dout.writeInt(dataLength);
    	dout.write(dataToSend,0,dataLength);
    	dout.flush();
    }

	public int getPortNumber() {
		return port;
	}
	
	public String toString() {
		return "\nIP: " + Arrays.toString(addr) + 
				"\nPort: " + port;
	}
}
