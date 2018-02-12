package cs455.overlay.util;

import java.util.ArrayList;

public class StatisticsCollectorAndDisplay {
	
	ArrayList<EntryStats> entryStats = new ArrayList<>();
	
	int sent;
	int received;
	int relayed;
	long sumDataSent;
	long sumDataReceived;
	
	public StatisticsCollectorAndDisplay() {
		// TODO Auto-generated constructor stub
	}

	public synchronized void addData(int nodeID, int sent, int received, int relayed, long sumDataSent, long sumDataReceived) {
		entryStats.add(new EntryStats(nodeID,sent,received,relayed,sumDataSent,sumDataReceived));
		this.sent+=sent;
		this.received+=received;
		this.relayed+=relayed;
		this.sumDataSent+=sumDataSent;
		this.sumDataReceived+=sumDataReceived;
	}
	
	@Override
	public String toString() {
		String string = "";
		string += "\n\tPackets Sent|Packets Received|Packets Relayed|Sum Values Sent|Sum Values Received";
		for(int i = 0; i < entryStats.size(); i++) {
			string += "\nNode " + entryStats.get(i);
		}
		string += "\nsum |"+ sent + "|" + received + "|" + relayed + "|" + sumDataSent + "|" + sumDataReceived;
		return string;
	}

}
