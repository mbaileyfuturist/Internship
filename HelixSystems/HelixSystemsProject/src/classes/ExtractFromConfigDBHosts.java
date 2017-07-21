package classes;

import java.util.Comparator;

import gov.nih.hpc.configdb.HostRecord;

public class ExtractFromConfigDBHosts {
	private String hostname, phase, description, lastModified, modifiedBy, installed, updated, serialNumber, location, hardware, ansibleGroup;

	public ExtractFromConfigDBHosts() {
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
	
	public ExtractFromConfigDBHosts(HostRecord hostRecord) {
		this.hostname = hostRecord.getHostname();
		this.phase = hostRecord.getPhase().toString();
		this.description = hostRecord.getDescription();
		this.lastModified = hostRecord.getLastModified().toString();
		this.modifiedBy = hostRecord.getModifiedBy();
		this.installed = hostRecord.getInstalled().toString();
		this.updated = hostRecord.getUpdated().toString();
		this.serialNumber = hostRecord.getSerialNumber();
		this.location = hostRecord.getLocation();
		this.hardware = hostRecord.getHardware();
		this.ansibleGroup = hostRecord.getAnsibleGroup();
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

class SortByHostName implements Comparator<ExtractFromConfigDBHosts>{

	@Override
	public int compare(ExtractFromConfigDBHosts arg0, ExtractFromConfigDBHosts arg1) {
		return arg0.getHostName().compareTo(arg1.getHostName());
	}
}
