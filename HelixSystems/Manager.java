import java.net.InetAddress;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Manager {

	Node node;
	Set hostnames = new HashSet();
	Set clusterNetAddressSet = new HashSet();
	Set helixnetAddressSet = new HashSet();
	
	public Set getHostnames() {
		return hostnames;
	}
	
	public void addElement(Node node){
		hostnames.add(node.getHostname());
		clusterNetAddressSet.add(node.getClusterNetAddress());
		helixnetAddressSet.add(node.getHelixnetAddress());
	}
	
	
}
