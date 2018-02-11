package cs455.overlay.util;

import java.util.Scanner;

import cs455.overlay.node.MessagingNode;
import cs455.overlay.node.Registry;

public class InteractiveCommandParser implements Runnable{
	
	private Scanner scan = new Scanner(System.in);
	
	private Registry registry;
	private MessagingNode messagingNode;
	
	public InteractiveCommandParser(Registry registry) {
		this.registry = registry;
	}
	
	public InteractiveCommandParser(MessagingNode mNode) {
		this.messagingNode = mNode;
	}
	
	public void run() {
		while(true) {
			String command = scan.nextLine();
			System.out.println("Command Entered: " + command);
		}
	}
}
