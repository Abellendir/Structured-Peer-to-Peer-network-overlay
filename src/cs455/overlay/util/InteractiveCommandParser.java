package cs455.overlay.util;

import java.util.Arrays;
import java.util.Scanner;

import cs455.overlay.node.Node;

/**
 * @author adam_
 *
 */
public class InteractiveCommandParser implements Runnable{
	
	private Scanner scan = new Scanner(System.in);
	
	private Node node;
	
	/**
	 * @param node
	 */
	public InteractiveCommandParser(Node node) {
		this.node = node;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		String[] command = {"",""};
		while(command[0] != "quit") {
			command = scan.nextLine().split(" ");
			if(command.length == 2) {
				node.interactiveCommandEvent(command);
			}else {
				node.interactiveCommandEvent(command[0]);
			}
			System.out.println("Command Entered: " + Arrays.toString(command));
		}
	}
}
