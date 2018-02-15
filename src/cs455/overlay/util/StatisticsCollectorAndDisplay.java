package cs455.overlay.util;

import java.util.ArrayList;

public class StatisticsCollectorAndDisplay {
	
	ArrayList<EntryStats> entryStats = new ArrayList<>();
	
	int sent = 0;
	int received = 0;
	int relayed = 0;
	long sumDataSent = 0;
	long sumDataReceived = 0;
	
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
	
	public synchronized void clear() {
		sent = 0;
		received = 0;
		relayed = 0;
		sumDataSent = 0;
		sumDataReceived = 0;
		entryStats = new ArrayList<>();
	}
	
	@Override
	public synchronized String toString() {
		String string = "";
		string += "\nNode ID|Packets Sent|Packets Received|Packets Relayed|Sum Values Sent|Sum Values Received";
		for(int i = 0; i < entryStats.size(); i++) {
			string += "\nNode " + entryStats.get(i);
		}
		string += "\nsum |"+ sent + "|" + received + "|" + relayed + "|" + sumDataSent + "|" + sumDataReceived;
		return string;
	}

}
