import java.net.InetAddress;
import java.util.Calendar;

public class Node {
	
	int requiredNumberOfUpdates;
	String hostname, systemStatus, operatingSystem, kernalVersion, personOfReference, type;
	Calendar timeOfLastCheckin, timeOfLastBoot;
	InetAddress clusterNetAddress, helixnetAddress;
	enum machineType {PHYSICAL, VIRTUAL, HYPERVISOR}
	enum runingGPFS {YES, NO}
	
	public Node(int requiredNumberOfUpdates, String hostname, String systemStatus, String operatingSystem,
			String kernalVersion, String personOfReference, Calendar timeOfLastCheckin, Calendar timeOfLastBoot,
			InetAddress clusterNetAddress, InetAddress helixInetAddress, String type) {
		this.requiredNumberOfUpdates = requiredNumberOfUpdates;
		this.hostname = hostname;
		this.systemStatus = systemStatus;
		this.operatingSystem = operatingSystem;
		this.kernalVersion = kernalVersion;
		this.personOfReference = personOfReference;
		this.timeOfLastCheckin = timeOfLastCheckin;
		this.timeOfLastBoot = timeOfLastBoot;
		this.clusterNetAddress = clusterNetAddress;
		this.helixnetAddress = helixnetAddress;
	}
	
	public machineType setType(){
		machineType type = null;
		
		if(this.type.equalsIgnoreCase("Physical")){
			type = type.PHYSICAL;
		}
		
		else if(this.type.equalsIgnoreCase("Virtual")){
			type = type.VIRTUAL;
		}
		
		else if(this.type.equalsIgnoreCase("HyperVisor")){
			type = type.HYPERVISOR;
		}
		
		return type;
	}

	public int getRequiredNumberOfUpdates() {
		return requiredNumberOfUpdates;
	}

	public void setRequiredNumberOfUpdates(int requiredNumberOfUpdates) {
		this.requiredNumberOfUpdates = requiredNumberOfUpdates;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getKernalVersion() {
		return kernalVersion;
	}

	public void setKernalVersion(String kernalVersion) {
		this.kernalVersion = kernalVersion;
	}

	public String getPersonOfReference() {
		return personOfReference;
	}

	public void setPersonOfReference(String personOfReference) {
		this.personOfReference = personOfReference;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Calendar getTimeOfLastCheckin() {
		return timeOfLastCheckin;
	}

	public void setTimeOfLastCheckin(Calendar timeOfLastCheckin) {
		this.timeOfLastCheckin = timeOfLastCheckin;
	}

	public Calendar getTimeOfLastBoot() {
		return timeOfLastBoot;
	}

	public void setTimeOfLastBoot(Calendar timeOfLastBoot) {
		this.timeOfLastBoot = timeOfLastBoot;
	}

	public InetAddress getClusterNetAddress() {
		return clusterNetAddress;
	}

	public void setClusterNetAddress(InetAddress clusterNetAddress) {
		this.clusterNetAddress = clusterNetAddress;
	}

	public InetAddress getHelixnetAddress() {
		return helixnetAddress;
	}

	public void setHelixnetAddress(InetAddress helixnetAddress) {
		this.helixnetAddress = helixnetAddress;
	}
}
