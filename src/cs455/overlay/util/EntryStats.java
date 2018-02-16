package cs455.overlay.util;

/**
 * @author adam bellendir
 *
 */
public class EntryStats {

	int nodeID, sent, received, relayed;
	long sumDataSent, sumDataReceived;

	/**
	 * @param nodeID
	 * @param sent
	 * @param received
	 * @param relayed
	 * @param sumDataSent
	 * @param sumDataReceived
	 */
	public EntryStats(int nodeID, int sent, int received, int relayed, long sumDataSent, long sumDataReceived) {
		this.nodeID = nodeID;
		this.sent = sent;
		this.received = received;
		this.relayed = relayed;
		this.sumDataSent = sumDataSent;
		this.sumDataReceived = sumDataReceived;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String string = "";
		string += nodeID + "|" + sent + "|" + received + "|" + relayed + "|" + sumDataSent + "|" + sumDataReceived;
		return string;
	}
}
