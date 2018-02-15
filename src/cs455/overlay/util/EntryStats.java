package cs455.overlay.util;

public class EntryStats {
	
	int nodeID;
	int sent;
	int received;
	int relayed;
	long sumDataSent;
	long sumDataReceived;

	public EntryStats(int nodeID, int sent, int received, int relayed, long sumDataSent, long sumDataReceived) {
		this.nodeID = nodeID;
		this.sent = sent;
		this.received = received;
		this.relayed = relayed;
		this.sumDataSent = sumDataSent;
		this.sumDataReceived = sumDataReceived;
	}
	
	@Override
	public String toString() {
		String string = "";
		string +=  nodeID + "|" + sent 
				+ "|" + received + "|" + relayed + "|" 
				+ sumDataSent + "|" + sumDataReceived;
		return string;
	}
}
