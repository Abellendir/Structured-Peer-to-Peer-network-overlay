# Structured-Peer-to-Peer-network-overlay
All commands that are prescribed by the assignment are implemented and function for the interactiveCommandParser's perspective.

Currently there is certainly an implementation failure in this and concurrentencie issue when starting the task or initiating 
task. This Code will initiate a task just find with a overlay size of 4 and 50000 messages. However if you attempt to do an overlay of 4 or 3 with say 100,000
messages it will initiate the task, but once each node sends it's messages no messages are received by any of the nodes.
To make matters more confusing if you start the task at 50000 with a overlay of 3 or 4 and incremently work your way up at 10,000 at a time you will be able
to run "start 100,000" and it will execute just fine.

## This project contains 5 packages
	-node
	-routing
	-transport
	-util
	-wireformats
	
### The node packages contains
	
|Classes|Description|
|:-------|:-----------|
|[Node](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/node/Node.java)|an interface for MessagingNode and Registry. With the methods onEvent and interactiveCommandParse|
|[Registry](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/node/Registry.java)|handles registration of nodes and initiating the primary task of tells the messaging nodes who to talk to and when to start  |
|[MessagingNode](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/node/MessagingNode.java)|handles that task of sending messages to other messagingNodes in the system. |
	
### The routing package contains

|Classes|Description|
|:-------|:-----------|
|[RoutingEntry](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/routing/RoutingEntry.java)| contains information about specifc connections and ids connected to the registry or the nodes that a messaging node can sender messages to|  
|[RoutingTable](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/routing/RoutingTable.java)| contains a list of all nodes in the registry and is stored in RoutingEntry as the routing table for each node in the registry.  |
	
### The transport package contains 

|Class|Description|
|:-------|:-----------| 
|[IncomingMessage](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/transport/IncomingMessage.java)| a Message class for communications to evenFactory|  
|[TCPConnection](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/transport/TCPConnection.java)| contains the receiver and sender threads as inner classes|  
|[TCPConnectionsCache](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/transport/TCPConnectionsCache.java)| holds every connection that is made to the respective node that connects to the messaging node|  
|[TCPServerThread](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/transport/TCPServerThread.java)| holds the serverSocket and allows a node to initiate communication with it|.  
	
### The util package contains

|Class|Description|
|:-------|:-----------| 
|[EntryStats](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/util/EntryStats.java)| a class that contains the statitics of every messaging node used only in registry to check the sums and print info about the completion of the task|  
|[InteractiveCommandParser](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/util/InteractiveCommandParser.java)| which is a thread that listeners for user input  |
|[StatisticsCollectorAndDisplay](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/util/StatisticsCollectorAndDisplay.java)| which holds all the sums for the task and stores the EntryStats for each node|  

### The wireformats package contains all the message types that could be sent or received by the registry and messager nodes 

Message WireFrames to properly format a message to be sent 

|Class|Description|
|:-----|:-----------|  
|[NodeReportsOverlaySetupStatus](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/NodeReportsOverlaySetupStatus.java)||  
|[OverlayNodeReportsTaskFinished](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/OverlayNodeReportsTaskFinished.java)|| 
|[OverlayNodeReportsTrafficSummary](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/OverlayNodeReportsTrafficSummary.java)||  
|[OverlayNodeSendsData](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/OverlayNodeSendsData.java)||
|[OverlayNodeSendsDeregistration](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/OverlayNodeSendsDeregistration.java)||  
|[OverlayNodeSendsRegistration](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/OverlayNodeSendsRegistration.java)|| 
|[RegistryReportsDeregistrationStatus](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/RegistryReportsDeregistrationStatus.java)||  
|[RegistryReportsRegistrationStatus](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/RegistryReportsRegistrationStatus.java)||
|[RegistryRequestsTaskInitiate](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/RegistryRequestsTaskInitiate.java)||  
|[RegistryRequestsTrafficSummary](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/RegistryRequestsTrafficSummary.java)||  
|[RegistrySendsNodeManifest](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/RegistrySendsNodeManifest.java)||  	 

Classes that determine what a message is  

|Class|Description|
|:-----|:-----------| 
|[Event](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/Event.java)| Interface for all the nodes containing getByte getType and toString for debuging|  
|[EventFactory](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/EventFactory.java)| builds the message |
|[Protocol](https://github.com/Abellendir/Structured-Peer-to-Peer-network-overlay/blob/master/src/cs455/overlay/wireformats/Protocol.java)| determines message type and returns a instance of it|
	
