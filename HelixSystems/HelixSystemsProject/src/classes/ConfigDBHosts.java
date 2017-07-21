package classes;

import gov.nih.hpc.configdb.HostRecord;

public class ConfigDBHosts {
	private String hostname, phase, description, lastModified, modifiedBy, installed, updated, serialNumber, location, hardware, ansibleGroup;

	public ConfigDBHosts() {
		hostname = "";
		phase = "";
		description = "";
		lastModified = "";
		modifiedBy = "";
		installed = "";
		updated = "";
		serialNumber = "";
		location = "";
		hardware = "";
		ansibleGroup = "";
	}
	
	
	public ConfigDBHosts(String hostname, String phase, String description, String lastModified, String modifiedBy,
			String installed, String updated, String serialNumber, String location, String hardware,
			String ansibleGroup) {
		this.hostname = hostname;
		this.phase = phase;
		this.description = description;
		this.lastModified = lastModified;
		this.modifiedBy = modifiedBy;
		this.installed = installed;
		this.updated = updated;
		this.serialNumber = serialNumber;
		this.location = location;
		this.hardware = hardware;
		this.ansibleGroup = ansibleGroup;
	}


	public String getHostName() {
		return hostname;
	}

	public void setHostName(String hostName) {
		this.hostname = hostName;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getInstalled() {
		return installed;
	}

	public void setInstalled(String installed) {
		this.installed = installed;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHardware() {
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	public String getAnsibleGroup() {
		return ansibleGroup;
	}

	public void setAnsibleGroup(String ansibleGroup) {
		this.ansibleGroup = ansibleGroup;
	}

	public String toString(){
		return getHostName() + " " + getPhase() + " " + getDescription() + " " + getLastModified() + " " + getModifiedBy() + " " + getInstalled() + " " + getUpdated() + " " + getSerialNumber() + " " + getLocation()
		+ " " + getHardware() + " " + getAnsibleGroup();
	}
}
