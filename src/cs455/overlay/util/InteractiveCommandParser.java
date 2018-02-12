package cs455.overlay.util;

import java.util.Arrays;
import java.util.Scanner;

import cs455.overlay.node.Node;

public class InteractiveCommandParser implements Runnable{
	
	private Scanner scan = new Scanner(System.in);
	
	private Node node;
	
	public InteractiveCommandParser(Node node) {
		this.node = node;
	}
	
	public void run() {
		while(true) {
			String[] command = scan.nextLine().split(" ");
			if(command.length == 2) {
				node.interactiveCommandEvent(command);
			}else {
				node.interactiveCommandEvent(command[0]);
			}
			System.out.println("Command Entered: " + Arrays.toString(command));
		}
	}
}
