package classes;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

public class ConfigDBInterfaces {
private int interfaceID, primary;
private String hostName, privateName, Interface, designation;
private InetAddress mac;
private Inet4Address ipv4;
private Inet6Address ipv6;

public ConfigDBInterfaces() {
	interfaceID = 0;
	primary = 0;
	hostName = "";
	privateName = "";
	Interface = "";
	designation = "";
	mac = null;
	ipv4 = null;
	ipv6 = null;
}

public ConfigDBInterfaces(int interfaceID, String hostName, String privateName, int primary, String Interface,
		String designation, InetAddress mac, Inet4Address ipv4, Inet6Address ipv6) {
	this.interfaceID = interfaceID;
	this.hostName = hostName;
	this.privateName = privateName;
	this.primary = primary;
	this.Interface = Interface;
	this.designation = designation;
	this.mac = mac;
	this.ipv4 = ipv4;
	this.ipv6 = ipv6;
}

public int getInterfaceID() {
	return interfaceID;
}

public void setInterfaceID(int interfaceID) {
	this.interfaceID = interfaceID;
}

public int getPrimary() {
	return primary;
}

public void setPrimary(int primary) {
	this.primary = primary;
}

public String getHostName() {
	return hostName;
}

public void setHostName(String hostName) {
	this.hostName = hostName;
}

public String getPrivateName() {
	return privateName;
}

public void setPrivateName(String privateName) {
	this.privateName = privateName;
}

public String getInterface() {
	return Interface;
}

public void setInterface(String interface1) {
	Interface = interface1;
}

public String getDesignation() {
	return designation;
}

public void setDesignation(String designation) {
	this.designation = designation;
}

public InetAddress getMac() {
	return mac;
}

public void setMac(InetAddress mac) {
	this.mac = mac;
}

public Inet4Address getIpv4() {
	return ipv4;
}

public void setIpv4(Inet4Address ipv4) {
	this.ipv4 = ipv4;
}

public Inet6Address getIpv6() {
	return ipv6;
}

public void setIpv6(Inet6Address ipv6) {
	this.ipv6 = ipv6;
}

public String toString(){
	return getInterfaceID() + " " + getPrimary() + " " + getHostName() + " " + getPrivateName() + " " + getInterface() + " " + getDesignation() + " " + getMac() 
	+ " " + getIpv4() + " " + getIpv6();
}
}
