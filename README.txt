All commands that are prescribed by the assignment are implemented and function for the interactiveCommandParser's perspective.

Currently there is certainly an implementation failure in this assignment and concurrentencie issue when starting the task or initiating 
task. This Code will initiate a task just find with a overlay size of 4 and 50000 messages. However if you attempt to do an overlay of 4 or 3 with say 100,000
messages it will initiate the task, but once each node sends it's messages no messages are received by any of the nodes.
To make matters more confusing if you start the task at 50000 with a overlay of 3 or 4 and incremently work your way up at 10,000 at a time you will be able
to run "start 100,000" and it will execute just fine. 

  



This project contains 5 packages
	-node
	-routing
	-transport
	-util
	-wireformats
	
The node packages contains
	-Node's an interface for MessagingNode and Registry. With the methods onEvent and interactiveCommandParser
	-Registry handles registration of nodes and initiating the primary task of tells the messaging nodes who to talk to and when to start
	-MessagingNode handles that task of sending messages to other messagingNodes in the system.
	
The routing package contains
	-RoutingEntry contains information about specifc connections and ids connected to the registry or the nodes that a messaging node can sender messages to
	-RoutingTable contains a list of all nodes in the registry and is stored in RoutingEntry as the routing table for each node in the registry.
		*Concurrent exception issues when trying to remove a node from the table* 
	
The transport package contains
	-IncomingMessage a Message class for communications to evenFactory
	-TCPConnection contains the receiver and sender threads as inner classes
	-TCPConnectionsCache holds every connection that is made to the respective node that connects to the messaging node
	-TCPServerThread holds the serverSocket and allows a node to initiate commincation with it.
	
The util package contains
	-EntryStats a class that contains the statitics of every messaging node used only in registry to check the sums and print info about the completion of the task
	-InteractiveCommandParser which is a thread that listeners for user input
	-StatisticsCollectorAndDisplay which holds all the sums for the task and stores the EntryStats for each node

The wireformats package contains all the message types that could be sent or received by the registry and messager nodes
	Message WireFrames to properly format a message to be sent
	-NodeReportsOverlaySetupStatus
	-OverlayNodeReportsTaskFinished
	-OverlayNodeReportsTrafficSummary
	-OverlayNodeSendsData
	-OVerlayNodeSendsDeregistration
	-OverlayNodeSendsRegistration
	-RegistryReportsDeregistrationStatus
	-RegistryReportsRegistrationStatus
	-RegistryRequestsTaskInitiate
	-RegistryRequestsTrafficSummary
	-RegistrySendsNodeManifest
	-Event Interface for all the nodes containing getByte getType and toString for debuging
	Classes that determine what a message is
	-EventFactory builds the message
	-Protocol determines message type and returns a instance of it
	