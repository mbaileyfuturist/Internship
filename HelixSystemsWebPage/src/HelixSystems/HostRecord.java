package HelixSystems;



public class HostRecord {

	private String hostname, os, kernel, ip_clusternet, ip_helixnet, repos, last_boot, last_checkin,
	last_yum_update, hosttype, virt_guests;
	private int update_packages, update_exception, security_packages, gpfs_configured, gpfs_mounted;
	
	public HostRecord() {
		this.hostname = "";
		this.os = "";
		this.kernel = "";
		this.ip_clusternet = "";
		this.ip_helixnet = "";
		this.repos = "";
		this.gpfs_configured = 0;
		this.gpfs_mounted = 0;
		this.last_boot = "";
		this.last_checkin = "";
		this.last_yum_update = "";
		this.hosttype = "";
		this.virt_guests = "";
		this.update_exception = 0;
		this.update_packages = 0;
		this.security_packages = 0;
	}
	
	public HostRecord(String hostname, String os, String kernel, String ip_clusternet, String ip_helixnet,
			String repos, int gpfs_configured, int gpfs_mounted, int update_packages, int security_packages, String last_boot, 
			String last_checkin, String last_yum_update, String hosttype, String virt_guests, int update_exception) {
		this.hostname = hostname;
		this.os = os;
		this.kernel = kernel;
		this.ip_clusternet = ip_clusternet;
		this.ip_helixnet = ip_helixnet;
		this.repos = repos;
		this.last_boot = last_boot;
		this.last_checkin = last_checkin;
		this.last_yum_update = last_yum_update;
		this.hosttype = hosttype;
		this.virt_guests = virt_guests;
		this.update_exception = update_exception;
		this.update_packages = update_packages;
		this.security_packages = security_packages;
		this.gpfs_configured = gpfs_configured;
		this.gpfs_mounted = gpfs_mounted;
	}

	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getKernel() {
		return kernel;
	}
	public void setKernel(String kernel) {
		this.kernel = kernel;
	}
	public String getIp_clusternet() {
		return ip_clusternet;
	}
	public void setIp_clusternet(String ip_clusternet) {
		this.ip_clusternet = ip_clusternet;
	}
	public String getIp_helixnet() {
		return ip_helixnet;
	}
	public void setIp_helixnet(String ip_helixnet) {
		this.ip_helixnet = ip_helixnet;
	}
	public String getRepos() {
		return repos;
	}
	public void setRepos(String repos) {
		this.repos = repos;
	}
	public int getGpfs_configured() {
		return gpfs_configured;
	}
	public void setGpfs_configured(int gpfs_configured) {
		this.gpfs_configured = gpfs_configured;
	}
	public int getGpfs_mounted() {
		return gpfs_mounted;
	}
	public void setGpfs_mounted(int gpfs_mounted) {
		this.gpfs_mounted = gpfs_mounted;
	}
	public String getLast_boot() {
		return last_boot;
	}
	public void setLast_boot(String last_boot) {
		this.last_boot = last_boot;
	}
	public String getLast_checkin() {
		return last_checkin;
	}
	public void setLast_checkin(String last_checkin) {
		this.last_checkin = last_checkin;
	}
	public String getLast_yum_update() {
		return last_yum_update;
	}
	public void setLast_yum_update(String last_yum_update) {
		this.last_yum_update = last_yum_update;
	}
	public String getHosttype() {
		return hosttype;
	}
	public void setHosttype(String hosttype) {
		this.hosttype = hosttype;
	}
	public String getVirt_guests() {
		return virt_guests;
	}
	public void setVirt_guests(String virt_guests) {
		this.virt_guests = virt_guests;
	}
	public int getUpdate_exception() {
		return update_exception;
	}
	public void setUpdate_exception(int update_exception) {
		this.update_exception = update_exception;
	}
	public int getUpdate_packages() {
		return update_packages;
	}
	public void setUpdate_packages(int update_packages) {
		this.update_packages = update_packages;
	}
	public int getSecurity_packages() {
		return security_packages;
	}
	public void setSecurity_packages(int security_packages) {
		this.security_packages = security_packages;
	} 
	public String hasGPFSNodes(){
		String value;
		if(getGpfs_mounted() == 1 || getGpfs_configured() == 1){
			value = "yes";
		}
		else{
			value = "no";
		}
		return value;
	}
}
