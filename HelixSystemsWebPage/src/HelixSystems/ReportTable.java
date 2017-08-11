package HelixSystems;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ReportTable {
	
	/**
	 * Displays a key in table format describing what each status icon represents.
	 * The icon descrition is in column 1 and the icon is in column 2.
	 * @return the table in string format.
	 */
	public static String key(){
		StringBuilder key = new StringBuilder("<table id = \"key\">");
		key.append("<tr>");
		key.append("<th colspan = \"2\">Status Key</th>");
		key.append("</tr>");
		key.append("<tr>");
		key.append("<td>System up to date.</td>");
		key.append("<td><img src=\"green_check.png\" width=\"15\"height=\"15\"></td>");
		key.append("</tr>");
		key.append("<tr>");
		key.append("<td>System has updates that should be installed.</td>");
		key.append("<td><img src=\"hourglass.png\" width=\"20\"height=\"20\"></td>");
		key.append("</tr>");
		key.append("<tr>");
		key.append("<td>System may be down.</td>");
		key.append("<td><img src=\"warning_sign.jpg\" width=\"20\"height=\"20\"></td>");
		key.append("</tr>");
		key.append("<tr>");
		key.append("<td>System is exempt from updates and may be down.</td>");
		key.append("<td><img src=\"blue_warning_sign.png\" width=\"24\"height=\"24\"></td>");
		key.append("</tr>");
		key.append("</table>");
		return key.toString();
	}
	
	/**
	 * Displays the header of the System Overview table. This header will include a form
	 * that contains functionality to sort and filter the rows based on the selected value.
	 * This header will also contain a searchbar that filters the rows as you search for a hostname.
	 * @return returns the contents of the header.
	 */
	public static String header(){
		StringBuilder header = new StringBuilder("<table id = \"system_overview\">");
		header.append("<!-- Table Header that contains sort and filter forms. -->");
		header.append("<tr>");
		header.append("<th colspan = \"11\">System Overview");
		header.append("<form action = \"sortBy.jsp\">");
		header.append("<label>Sort By</label>");
		header.append("<select name = \"sortBy\">");
		header.append("<option value = \"none\">none</option>");
		header.append("<option value = \"lastCheckin_ascending\">last checkin</option>");
		header.append("<option value = \"lastboot_ascending\">last boot</option>");
		header.append("<option value = \"lastCheckin_descending\">last checkin descending</option>");
		header.append("<option value = \"lastboot_descending\">last boot descending</option>");
		header.append("</select>");
		header.append("<label>Filter by</label>");
		header.append("<select name = \"filterBy\">");
		header.append("<option value = \"none\">none</option>");
		header.append("<option value = \"upToDate\">up to date</option>");
		header.append("<option value = \"needsUpdates\">needs updates</option>");
		header.append("<option value = \"GPFSNodes\">running GPFS nodes</option>");
		header.append("<option value = \"physical\">physical machine</option>");
		header.append("<option value = \"virtual\">virtual machine</option>");
		header.append("<option value = \"hypervisor\">hypervisor</option>");
		header.append("</select>");
		header.append("<input type = \"Submit\" value = \"Submit\">");
		header.append("<input type=\"text\" id=\"myInput\" onkeyup=\"searchFilter()\" placeholder=\"Search for hostnames..\">");
		header.append("</form>");
		header.append("</th>");
		header.append("</tr>");
		return header.toString();
	}
	
	/**
	 * One row that displays the names of each column.
	 * @return The column titles.
	 */
	public static String columnNames(){
		StringBuilder names = new StringBuilder("<!-- column titles -->");
		names.append("<tr class = \"column_title\">");
		names.append("<td><strong>Status</strong></td>");
		names.append("<td><strong>Hostname</strong></td>");
		names.append("<td><strong>Description</strong></td>");
		names.append("<td><strong>Update Packages</strong></td>");
		names.append("<td  colspan = \"2\"><strong>os</strong></td>");
		names.append("<td><strong>Kernal Version</strong></td>");
		names.append("<td><strong>Machine Type</strong></td>");
		names.append("<td><strong>last Check-in</strong></td>");
		names.append("<td><strong>Running GPFS Nodes</strong></td>");
		names.append("<td><strong>Person of Reference</strong></td>");
		names.append("</tr>");
		return names.toString();
	}
	
	/**
	 * The end of the table.
	 * @return the closing tag for the table.
	 */
	public static String footer(){
		return "</table>";
	}	
		
	/**
	 * Represents a tablerow of the System Overview table that displays the status, 
	 * hostname, operating system, kernel,host type, last time checked in and if it 
	 * is running GPFS nodes or not.
	 * @param hostRecord
	 * @return The table row.
	 */
	public static String trEntry(HostRecord hostRecord){
		StringBuilder tableRow = new StringBuilder("<tr>");
		String status ="";
		if(hostRecord.getUpdate_packages() == 0){
			status = "<img src=\"green_check.png\" width=\"20\"height=\"20\">";
		}else{
			status = "<img src=\"hourglass.png\" width=\"20\"height=\"20\">";
		}
		  tableRow.append("<td>" + status +"</td>"); 
		  tableRow.append("<td><a href =\"javascript:void(0)\" onclick=\"toggle_visibility('popup-box');\">" + hostRecord.getHostname() + "</td>"); 
		  tableRow.append("<td>" + hostRecord.getOs() + "</td>"); 
		  tableRow.append("<td>" + hostRecord.getKernel() + "</td>");
		  tableRow.append("<td>" + hostRecord.getHosttype() + "</td>"); 
		  tableRow.append("<td>" + hostRecord.getLast_checkin() + "</td>");
		  tableRow.append("<td>" + hostRecord.hasGPFSNodes() + "</td>");
		  tableRow.append("<td>Blank</td>");
		  tableRow.append("</tr>");
		return tableRow.toString();
	}
	
	/**
	 * Representing the System Overview table this method displays the full table of hosts within the 
	 * helixsystems database. Given a collection of hostRecords this table will display each hostRecords 
	 * current status, hostname, operating system, kernel, host type, last time checked in and if its
	 * running GPFS nodes or not.
	 * @param hosts
	 * @return The full table.
	 */
	public static String fullTable(Collection<HostRecord> hosts){
		String src = "";
		StringBuilder table = new StringBuilder("");
		Iterator<HostRecord> iterator = hosts.iterator();
		while(iterator.hasNext()){
			table.append("<tr class=\"rows\">");
			HostRecord hostRecord = iterator.next();
			if(hostRecord.getOs().substring(0, 6).equals("CentOS")){
				 src = "cent_os.jpeg";
			}else{
				src = "redhat.jpg";
			}
			String status ="";
			if(hostRecord.getUpdate_packages() == 0){
				status = "<img src=\"green_check.png\" width=\"20\"height=\"20\" title=\"System up to date.\">";
			}else{
				status = "<img src=\"hourglass.png\" width=\"20\"height=\"20\" title=\"System has updates that should be installed.\">";
			}
			table.append("<td>" + status +"</td>"); 
			table.append("<td><a href =\"javascript:void(0)\" onclick=\"toggle_visibility('popup-box-" + hostRecord.getHostname() + "');\">" + hostRecord.getHostname() + "</td>");
			table.append("<td>" + ConfigDbTest.getDescription(hostRecord.getHostname()) + "</td>");
			table.append("<td>" + hostRecord.getUpdate_packages() + "</td>"); 
			table.append("<td><img src= \"" + src + "\" width=\"50\" height=\"45\"/></td>"); 
			table.append("<td>" + hostRecord.getOs() + "</td>"); 
			table.append("<td>" + hostRecord.getKernel() + "</td>");
			table.append("<td>" + hostRecord.getHosttype() + "</td>") ;
			table.append("<td>" + hostRecord.getLast_checkin() + "</td>");
			table.append("<td>" + hostRecord.hasGPFSNodes() + "</td>");
			table.append("<td>" + ConfigDbTest.getPerosonOfReference(hostRecord.getHostname())+ "</td>");
			table.append("</tr>");
			table.append("\n");
		}
		return table.toString();
	}
	
	/**
	 * Represents a popup box that displays the hostname and status in the title and attribubtes 
	 * about the hostname that has been passed in the second parameter. These attributes inlude 
	 * the following, a table called system info that displays the hosts hostname, kernal, 
	 * GPFS configured, GPFS mounted. It will also display a System Events table that 
	 * displays information on the last time the host checked in, the last time the host booted 
	 * and the last Yum update that host had. There will also be a table displaying the hosts Yum
	 * repositories, a two tables that will display the hosts clusternet address and their 
	 * helixnet address and virtual guests if applicable.
	 * address 
	 * @param hostRecord: a collection of hostRecords.
	 * @param hostname: A hostname.
	 * @return The popup box.
	 */
	public static String popUp(Collection<HostRecord> hostRecord, String hostname){ 
			
			StringBuilder popUp = new StringBuilder("<!--Popup container-->");
			popUp.append("<div id=\"popup-box-"+ hostname +"\" class=\"popup-position\">");
			popUp.append("<div id=\"popup-wrapper\">");
			popUp.append("<div id=\"popup-container\">");
			Iterator<HostRecord> iterate = hostRecord.iterator();
			while(iterate.hasNext()){
				HostRecord hostRecordObj = iterate.next();
				if(hostRecordObj.getHostname().equals(hostname)){
					String status ="";
					if(hostRecordObj.getUpdate_packages() == 0){
						status = "System up tp date.";
					}else{
						status = "System has updates that need to be installed.";
					}
					popUp.append("<p>" + "<strong>" + hostRecordObj.getHostname() + "</strong>: " + status + "</p>");
					popUp.append("<hr style = \"clear:left\">");
					
					//System Info Table.
					popUp.append("<!--System Info Table-->");
					popUp.append("<table id=\"system_info\">");
					popUp.append("<tr>");
					popUp.append("<th colspan = \"2\">System Info</th>");
					popUp.append("</th>");
					popUp.append("</tr>");
					popUp.append("<tr>");
					popUp.append("<td>Hostname:</td>");
					popUp.append("<td>" + hostRecordObj.getHostname() + "</td>");
					popUp.append("</tr>");
					popUp.append("<tr>");
					popUp.append("<td>Kernel:</td>");
					popUp.append("<td>" + hostRecordObj.getKernel() + "</td>");
					popUp.append("</tr>");
					popUp.append("<tr>");
					popUp.append("<td>GPFS Configured:</td>");
					String configuredValue = "";
					String mountedValue = "";
					if(hostRecordObj.getGpfs_configured() == 0){
						configuredValue = "No";
					}else{
						configuredValue = "Yes";
					}
					popUp.append("<td>" + configuredValue + "</td>");
					popUp.append("<tr>");
					popUp.append("<td>GPFS Mounted:</td>");
					if(hostRecordObj.getGpfs_mounted() == 0){
						mountedValue = "No";
					}else{
						mountedValue = "Yes";
					}
					popUp.append("<td>" + mountedValue + "</td>");
					popUp.append("</tr>");
					popUp.append("</tr>");
					popUp.append("</table>");
			
					//System Events Table.
					popUp.append("<!--System Events Table-->");
					popUp.append("<table id=\"system_events\">");
					popUp.append("<tr>");
					popUp.append("<th colspan = \"2\">System Events</th>");
					popUp.append("</tr>");
					popUp.append("<tr>");
					popUp.append("<td>Check in:</td>");
					popUp.append("<td>" + hostRecordObj.getLast_checkin() + "</td>");
					popUp.append("</tr>");
					popUp.append("<tr>");
					popUp.append("<td>Last Boot:</td>");
					popUp.append("<td>" + hostRecordObj.getLast_boot() + "</td>");
					popUp.append("</tr>");
					popUp.append("<tr>");
					popUp.append("<td>Yum Update:</td>");
					popUp.append("<td>" + hostRecordObj.getLast_yum_update() + "</td>");
					popUp.append("</tr>");
					popUp.append("</table>");
					
					//Yum Repository Table.
					ArrayList<String> repos = new ArrayList<>();
					repos = Splitter.getRepos(hostRecordObj.getRepos());
					popUp.append("<!--Yum Repository Table-->");
					popUp.append("<table id=\"yum_repositories\">");
					popUp.append("<tr>");
					popUp.append("<th colspan = \"2\">Yum Repositories</th>");
					popUp.append("</tr>");
					for(String repo: repos){
						popUp.append("<tr>");
						popUp.append("<td>" + repo + "</td>");
						popUp.append("</tr>");
					}
					popUp.append("</table>");
					
					//HelixnetAddresses table
					popUp.append("<!--HelixnetAddresses Table-->");
					popUp.append("<table id=\"helixnet_addresses\">");
					popUp.append("<tr>");
					popUp.append("<th>HelixNet Address (eth0)</th>");
					popUp.append("</tr>");
					Set<InetAddress> helixnetAddresses = new HashSet<InetAddress>();
					helixnetAddresses = ConfigDbTest.hostToHelixnet(hostRecordObj.getHostname());
					for(InetAddress helixnetAddress: helixnetAddresses){
					popUp.append("<tr>");
					popUp.append("<td>" + helixnetAddress.getHostAddress() + "</td>");
					popUp.append("</tr>");
					}
					popUp.append("</table>");
					
					//ClusternetAddresses table
					popUp.append("<!--ClusternetAddresses Table-->");
					popUp.append("<table id=\"clusternet_addresses\">");
					popUp.append("<tr>");
					popUp.append("<th>ClusterNet Addresses (eth0)</th>");
					popUp.append("</tr>");
					
					Set<InetAddress> clusternetAddresses = ConfigDbTest.hostToClusternet(hostRecordObj.getHostname());
					for(InetAddress clusternetAddress: clusternetAddresses){
					popUp.append("<tr>");
					popUp.append("<td>" + clusternetAddress.getHostAddress() + "</td>");
					popUp.append("</tr>");
					}
					popUp.append("</table>");
					
					Collection<Inet6Address> Ipv6Addresses = ConfigDbTest.getIpv6Addresses(hostname);
					if(Ipv6Addresses.size() > 0){
					//IPv6 Table.
					popUp.append("<!--IPv6 Table-->");
					popUp.append("<table id=\"IPv6\">");
					popUp.append("<tr>");
					popUp.append("<th>IPv6 Addresses</th>");
					popUp.append("</tr>");
					
					for(Inet6Address Ipv6Address: Ipv6Addresses){
					popUp.append("<tr>");
				    popUp.append("<td>" + Ipv6Address.getHostName()+ "</td>");
					popUp.append("</tr>");
					}
					popUp.append("</table>");
					}
					
					//Virtual Guests table.
					if(hostRecordObj.getHosttype().equals("hypervisor")){
						popUp.append("<!--VirtualGuests Table-->");
						popUp.append("<table id=\"virtual_guests\">");
						popUp.append("<tr>");
						popUp.append("<th colspan = \"2\">Virtual Guests</th>");
						popUp.append("</tr>");
						
						Map<String, String> virtualGuests = Splitter.getGuests(hostRecordObj.getVirt_guests());
						
						popUp.append("<tr>");
						popUp.append("<td>Guestname</td>");
						popUp.append("<td>Status</td>");
						popUp.append("</tr>");
						for(String guestKey: virtualGuests.keySet()){
							popUp.append("<tr>");
							popUp.append("<td>" + guestKey +"</td>");
							popUp.append("<td>" + virtualGuests.get(guestKey) + "</td>");
							popUp.append("</tr>");
						}
						popUp.append("</table>");
					}
				}
			}
			popUp.append("<p id = \"close\"><a href=\"javascript:void(0)\" onclick=\"toggle_visibility('popup-box-" + hostname + "');\">Close popup box.</a></p>");
			popUp.append("</div> <!--Popup-container end-->");
			popUp.append("</div> <!--Popup-wrapper end-->");
			popUp.append("</div> <!--Popup-box end-->");
		return popUp.toString();
	}
		
}