/**
 *
 */
package cs455.overlay.transport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * @Author Adam Bellendir
 */
public class TCPConnection implements Runnable{

	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;

    /**
     *
     * @throws IOException 
     *
     */
	public TCPConnection(Socket socket) throws IOException {
        this.socket = socket;
        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());
	}

    /**
     *
     */
    public void run() {

        int dataLength;
        while(socket != null){
            try{
                dataLength = din.readInt();
                byte[] data = new byte[dataLength];

            }catch(SocketException se){
                System.out.println(se.getMessage());
                break;
            }catch(IOException ioe){
                System.out.println(ioe.getMessage());
                break;
            }
        }

    }

}
