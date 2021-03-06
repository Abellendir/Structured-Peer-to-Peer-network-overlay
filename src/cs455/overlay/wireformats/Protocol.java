package cs455.overlay.wireformats;

import java.io.IOException;

/**
 * Contains all message types
 * as final
 * @author Adam Bellendir
 *
 */
public interface Protocol {
	
	public static final int OVERLAY_NODE_SENDS_REGISTRATION = 2;
	public static final int REGISTRY_REPORTS_REGISTRATION_STATUS = 3;
	public static final int OVERLAY_NODE_SENDS_DEREGISTRATION = 4;
	public static final int REGISTRY_REPORTS_DEREGISTRATION_STATUS = 5;
	public static final int REGISTRY_SENDS_NODE_MANIFEST = 6;
	public static final int NODE_REPORTS_OVERLAY_SETUP_STATUS = 7;
	public static final int REGISTRY_REQUESTS_TASK_INITIATE = 8;
	public static final int OVERLAY_NODE_SENDS_DATA = 9;
	public static final int OVERLAY_NODE_REPORTS_TASK_FINISHED = 10;
	public static final int REGISTRY_REQUEST_TRAFFIC_SUMMARY = 11;
	public static final int OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY = 12;
	
	/**
	 * @param type
	 * @param marshalledBytes
	 * @return
	 * @throws IOException
	 */
	public default Event getEvent(int type, byte[] marshalledBytes) throws IOException {
		switch(type) {
			case OVERLAY_NODE_SENDS_REGISTRATION:
				return new OverlayNodeSendsRegistration(marshalledBytes);
			case REGISTRY_REPORTS_REGISTRATION_STATUS:
				return new RegistryReportsRegistrationStatus(marshalledBytes);
			case OVERLAY_NODE_SENDS_DEREGISTRATION:      
				return new OverlayNodeSendsDeregistration(marshalledBytes);
			case REGISTRY_REPORTS_DEREGISTRATION_STATUS: 
				return new RegistryReportsDeregistrationStatus(marshalledBytes);
			case REGISTRY_SENDS_NODE_MANIFEST:           
				return new RegistrySendsNodeManifest(marshalledBytes);
			case NODE_REPORTS_OVERLAY_SETUP_STATUS:      
				return new NodeReportsOverlaySetupStatus(marshalledBytes);
			case REGISTRY_REQUESTS_TASK_INITIATE:        
				return new RegistryRequestsTaskInitiate(marshalledBytes);
			case OVERLAY_NODE_SENDS_DATA:                
				return new OverlayNodeSendsData(marshalledBytes);
			case OVERLAY_NODE_REPORTS_TASK_FINISHED: 	 
				return new OverlayNodeReportsTaskFinished(marshalledBytes);
			case REGISTRY_REQUEST_TRAFFIC_SUMMARY: 		 
				return new RegistryRequestsTrafficSummary(marshalledBytes);
			case OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY:	 
				return new OverlayNodeReportsTrafficSummary(marshalledBytes);
			default: break;
		}
		return null;
	}
}
