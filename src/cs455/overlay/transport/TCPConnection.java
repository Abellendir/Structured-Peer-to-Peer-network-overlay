/**
 *
 */
package cs455.overlay.transport;

/**
 * @Author Adam Bellendir
 */
public class TCPConnection {

	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;

    /**
     *
     */
	public TCPConnection(Socket socket) {
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
